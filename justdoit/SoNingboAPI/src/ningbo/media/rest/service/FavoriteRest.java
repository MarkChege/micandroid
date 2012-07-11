package ningbo.media.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ningbo.media.bean.Favorite;
import ningbo.media.bean.FavoriteTemp;
import ningbo.media.bean.Location;
import ningbo.media.bean.SystemUser;
import ningbo.media.bean.TempUser;
import ningbo.media.bean.enums.FavoriteType;
import ningbo.media.data.api.FavoriteList;
import ningbo.media.data.entity.LocationDetail;
import ningbo.media.rest.util.Constant;
import ningbo.media.rest.util.JSONCode;
import ningbo.media.rest.util.StringUtils;
import ningbo.media.service.FavoriteService;
import ningbo.media.service.FavoriteTempService;
import ningbo.media.service.LocationService;
import ningbo.media.service.SystemUserService;
import ningbo.media.service.TempUserService;
import ningbo.media.util.MD5;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/favorite")
@Component
@Scope("request")
public class FavoriteRest {

	@Resource
	private FavoriteService favoriteService;

	@Resource
	private FavoriteTempService favoriteTempService;

	@Resource
	private SystemUserService systemUserService;

	@Resource
	private LocationService locationService;

	@Resource
	private TempUserService tempUserService;

	@Path("/location/count/{locationId : \\d+}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFavoriteCountByLocationId(@PathParam("locationId")
	String locationId) throws JSONException {
		List<Favorite> list = favoriteService.getList(Constant.LOCATIONID,
				Integer.valueOf(locationId));
		JSONObject json = new JSONObject();
		if (null == list || list.size() < 0) {
			json.put(Constant.FAVORITENUMBER, 0);
			return Response.ok(json.toString()).build();
		} else {
			json.put(Constant.FAVORITENUMBER, list.size());
			return Response.ok(json.toString()).build();
		}
	}

	@Path("/delete")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFavorite(@FormParam("locationId")
	String locationId, @FormParam("userId")
	String userId, @FormParam("deviceId")
	String deviceId, @FormParam("key")
	String key) throws JSONException {
		JSONObject json = new JSONObject();
		if (!StringUtils.hasText(key)) {
			json.put(Constant.CODE, JSONCode.GLOBAL_KEYISNULL);
			return Response.ok(json.toString()).build();
		} else if (!Constant.KEY.equals(key)) {
			json.put(Constant.CODE, JSONCode.GLOBAL_KEYINPUTINVALID);
			return Response.ok(json.toString()).build();
		}

		if ((!StringUtils.hasText(userId)) && (!StringUtils.hasText(deviceId))) {
			json.put(Constant.CODE, JSONCode.FAVORITE_INPUT_INVALID);
			return Response.ok(json.toString()).build();
		}

		if (StringUtils.hasText(userId)) {
			Favorite favorite = favoriteService.getFavoriteByUserId(Integer
					.valueOf(userId), Integer.valueOf(locationId));
			if (null == favorite) {
				json.put(Constant.RESULT, JSONCode.RESULT_FAIL) ;
				json.put(Constant.MESSAGE, JSONCode.MSG_FAVORITE_USER_NOEXISTS);
				return Response.ok(json.toString()).build();
			} else {
				favoriteService.delete(favorite);
				json.put(Constant.RESULT, JSONCode.RESULT_SUCCESS) ;
				json.put(Constant.MESSAGE, JSONCode.MSG_FAVORITE_USER_DELETE_SUCCESS);
				return Response.ok(json.toString()).build();
			}
		} else {
			TempUser tmpUser = tempUserService.get(Constant.DEVICEID, deviceId);
			if (null != tmpUser) {
				Integer tmpId = tmpUser.getId() ;
				FavoriteTemp favorite = favoriteTempService
						.getFavoriteTempByDeviceId(tmpId, Integer
								.valueOf(locationId));
				if (null == favorite) {
					json.put(Constant.RESULT, JSONCode.RESULT_FAIL) ;
					json.put(Constant.MESSAGE, JSONCode.MSG_FAVORITE_TEMPUSER_NOEXISTS);
					return Response.ok(json.toString()).build();
				} else {
					favoriteTempService.delete(favorite);
					json.put(Constant.RESULT, JSONCode.RESULT_SUCCESS) ;
					json.put(Constant.MESSAGE, JSONCode.MSG_FAVORITE_TEMPUSER_DELETE_SUCCESS);
					return Response.ok(json.toString()).build();
				}
			}

		}
		json.put(Constant.RESULT, JSONCode.RESULT_FAIL) ;
		json.put(Constant.MESSAGE, JSONCode.MSG_FAVORITE_DELETE_FAIL);
		return Response.ok(json.toString()).build();

	}

	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUserFavorite(@FormParam("userId")
	String userId, @FormParam("locationId")
	String locationId, @FormParam("deviceId")
	String deviceId, @FormParam("key")
	String key) throws JSONException {
		JSONObject tempJson = new JSONObject();
		try {
			TempUser tempUser = null;
			Favorite fav = null;
			FavoriteTemp favTemp = null;
			if (!StringUtils.hasText(key)) {
				tempJson.put(Constant.CODE, JSONCode.GLOBAL_KEYISNULL);
				return Response.ok(tempJson.toString()).build();
			} else if (!Constant.KEY.equals(key)) {
				tempJson.put(Constant.CODE, JSONCode.GLOBAL_KEYINPUTINVALID);
				return Response.ok(tempJson.toString()).build();
			}
			if (!StringUtils.hasText(locationId)) {
				tempJson.put(Constant.CODE, JSONCode.FAVORITE_LOCATIONISNULL);
				return Response.ok(tempJson.toString()).build();
			}
			Location loc = locationService.get(Integer.valueOf(locationId));
			if (null == loc) {
				tempJson.put(Constant.RESULT, JSONCode.RESULT_FAIL);
				tempJson.put(Constant.MESSAGE, JSONCode.MSG_LOCATION_NOEXISTS);
				return Response.ok(tempJson.toString()).build();
			}

			if (userId != null && userId.trim().length() > 0) {
				SystemUser systemUser = systemUserService.get(Integer
						.valueOf(userId));
				if (null == systemUser) {
					tempJson.put(Constant.RESULT, JSONCode.RESULT_FAIL);
					tempJson.put(Constant.MESSAGE, JSONCode.MSG_USER_NOEXISTS);
					return Response.ok(tempJson.toString()).build();
				}

				fav = favoriteService.getFavoriteByUserId(Integer
						.valueOf(userId), Integer.valueOf(locationId));

				if (null != fav) {
					tempJson.put(Constant.MESSAGE,
							JSONCode.MSG_FAVORITE_ALREADY_EXIST);
					tempJson.put(Constant.RESULT, JSONCode.RESULT_FAIL);
					return Response.ok(tempJson.toString()).build();
				}

				fav = new Favorite();
				fav.setUserId(Integer.valueOf(userId));
				fav.setLocationId(Integer.valueOf(locationId));
				favoriteService.save(fav);
			} else {
				if (!StringUtils.hasText(deviceId)) {
					tempJson.put(Constant.CODE, JSONCode.FAVORITE_GET_SERIAL);
					return Response.ok(tempJson.toString()).build();
				} else {
					Integer tmpUserId = 0;
					boolean temp = tempUserService.isExistsDeviceId(deviceId);
					if (!temp) {
						tempUser = new TempUser();
						String temp_name = String.valueOf(System
								.currentTimeMillis());
						tempUser.setTempName(temp_name);
						tempUser.setDeviceId(deviceId);
						tempUser.setTempKey(Constant.KEY);
						tmpUserId = tempUserService.save(tempUser);
					} else {
						TempUser t = tempUserService.get(Constant.DEVICEID,
								deviceId);
						if (null != t) {
							tmpUserId = t.getId();
						}
					}

					favTemp = favoriteTempService.getFavoriteTempByDeviceId(
							tmpUserId, Integer.valueOf(locationId));
					if (null != favTemp) {
						tempJson.put(Constant.MESSAGE,
								JSONCode.MSG_FAVORITE_ALREADY_EXIST);
						tempJson.put(Constant.RESULT, JSONCode.RESULT_FAIL);
						return Response.ok(tempJson.toString()).build();
					}

					favTemp = new FavoriteTemp();
					favTemp.setTempId(tmpUserId);
					favTemp.setLocationId(Integer.valueOf(locationId));
					favoriteTempService.save(favTemp);

				}
			}
			tempJson.put(Constant.MESSAGE,
					JSONCode.MSG_FAVORITE_LOCATION_SUCCESS);
			tempJson.put(Constant.RESULT, JSONCode.RESULT_SUCCESS);
			return Response.ok(tempJson.toString()).build();
		} catch (NumberFormatException ex) {
			tempJson.put(Constant.CODE, JSONCode.FAVORITE_INPUT_INVALID);
			return Response.ok(tempJson.toString()).build();
		}

	}

	@Path("/user/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserFavorite(@PathParam("userId")
	String userId) {
		return Response.ok(queryUserFavorites(userId)).build();
	}

	@Path("/device/{deviceId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeviceFavorite(@PathParam("deviceId")
	String deviceId) {
		return Response.ok(queryDeviceFavorites(deviceId)).build();
	}

	private FavoriteList queryUserFavorites(String userId) {
		List<Favorite> list = favoriteService.getListFavoriteById(userId,
				FavoriteType.REALUSER);
		if (null == list || list.size() < 0) {
			return new FavoriteList();
		}
		List<LocationDetail> locations = new ArrayList<LocationDetail>();

		for (Favorite f : list) {
			LocationDetail detail = new LocationDetail();
			Integer locationId = f.getLocationId();
			Location tempLocation = null;
			if (null != locationId) {
				tempLocation = locationService.get(locationId);
				detail.setMd5Value(MD5.calcMD5(String.valueOf(tempLocation
						.getId())));
				detail.setName_cn(tempLocation.getName_cn());
				detail.setAddress_cn(tempLocation.getAddress_cn());
				detail.setLatitude(tempLocation.getLatitude());
				detail.setLongitude(tempLocation.getLongitude());
				locations.add(detail);
			}
		}
		return new FavoriteList(Integer.valueOf(userId), "", locations);
	}

	private FavoriteList queryDeviceFavorites(String deviceId) {
		List<Favorite> list = favoriteService.getListFavoriteById(deviceId,
				FavoriteType.TEMPUSER);
		if (null == list || list.size() < 0) {
			return new FavoriteList();
		}
		List<LocationDetail> locations = new ArrayList<LocationDetail>();

		for (Favorite f : list) {
			LocationDetail detail = new LocationDetail();
			Integer locationId = f.getLocationId();
			Location tempLocation = null;
			if (null != locationId) {
				tempLocation = locationService.get(locationId);
				detail.setMd5Value(MD5.calcMD5(String.valueOf(tempLocation
						.getId())));
				detail.setName_cn(tempLocation.getName_cn());
				detail.setAddress_cn(tempLocation.getAddress_cn());
				detail.setLatitude(tempLocation.getLatitude());
				detail.setLongitude(tempLocation.getLongitude());
				locations.add(detail);
			}
		}
		return new FavoriteList(Integer.valueOf(-1), deviceId, locations);
	}


}
