package ningbo.media.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import ningbo.media.bean.ImageInformation;
import ningbo.media.bean.ModuleFile;
import ningbo.media.core.service.impl.BaseServiceImpl;
import ningbo.media.dao.ModuleFileDao;
import ningbo.media.rest.dto.ModuleFileData;
import ningbo.media.service.ModuleFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("moduleFileServie")
public class ModuleFileServiceImpl extends BaseServiceImpl<ModuleFile, Integer>
		implements ModuleFileService {

	@Resource
	private ModuleFileDao moduleFileDao;
	
	

	@Autowired
	public ModuleFileServiceImpl(@Qualifier("moduleFileDao")
	ModuleFileDao moduleFileDao) {
		super(moduleFileDao);
	}

	

	public List<ModuleFileData> queryModuleFileByUserHeader(Integer userId) {
		String hql = "select m from ModuleFile as m join m.systemUsers as u where 1=1 and u.id = ? " ;
		
		List<ModuleFile> list = moduleFileDao.findByHql(hql,userId) ;
		List<ModuleFileData> data = new ArrayList<ModuleFileData>();
		if(null != list && list.size() > 0){
			for(ModuleFile file : list){
				ModuleFileData temp = new ModuleFileData();
				temp.setId(file.getId()) ;
				temp.setFileName(file.getFileName()) ;
				temp.setFilePath(file.getFileHash()) ;
				temp.setWidth(file.getImageInfo().getWidth()) ;
				temp.setHeight(file.getImageInfo().getHeight()) ;
				
				data.add(temp) ;
			}
			return data ;
		}
		return null;
	}
	
	public List<ModuleFile> queryModuleFileByType(Integer userId,
			Integer toolId, Integer typeId) {
		final String hql = "from ModuleFile as m where 1=1 and m.tools.id in (select id from Tools as t inner join SystemUser as u where 1=1 and u.id = ? and t.id = ?) and m.moduleType.id = ? ";
		List<ModuleFile> list = moduleFileDao.findByHql(hql, userId, toolId,
				typeId);
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
	}


	public List<ModuleFileData> queryAllFile() {
		List<ModuleFile> listFiles = moduleFileDao.getAll() ;
		List<ModuleFileData> data = new ArrayList<ModuleFileData>() ;
		if(null == listFiles || listFiles.size() < 0){
			return null ;
		}
		for(ModuleFile file : listFiles){
			ModuleFileData temp = new ModuleFileData() ;
			temp.setId(file.getId()) ;
			temp.setFileName(file.getFileName()) ;
			temp.setFilePath(file.getFileHash()) ;
			
			ImageInformation infor = file.getImageInfo() ;
			if(null != infor){
				temp.setWidth(infor.getWidth()) ;
				temp.setHeight(infor.getHeight()) ;
				temp.setSize(infor.getSize()) ;
				temp.setLongitude(infor.getLongitude()) ;
				temp.setLatitude(infor.getLatitude()) ;
			}
			data.add(temp) ;
		}
		return data;
	}


	
}
