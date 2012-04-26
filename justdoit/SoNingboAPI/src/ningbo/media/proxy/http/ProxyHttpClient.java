package ningbo.media.proxy.http;

import java.io.File;
import java.util.List;

import ningbo.media.proxy.bean.FormParamter;
import ningbo.media.proxy.util.HttpUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyHttpClient {

	private static final int CONNECTION_TIMEOUT = 2000000;

	private static Logger log = LoggerFactory.getLogger(ProxyHttpClient.class);

	/**
	 * Using GET method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpGet(String url, String queryString) throws Exception {
		String responseData = "";
		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}

		log.info("httpGet[1] url:" + url);
		HttpClient httpClient = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		httpGet.getParams().setParameter("http.socket.timeout",
				new Integer(CONNECTION_TIMEOUT));

		try {
			int statusCode = httpClient.executeMethod(httpGet);
			if (statusCode != HttpStatus.SC_OK) {
				log
						.info("HttpGet[2] Method failed: "
								+ httpGet.getStatusLine());
			}
			// Read the response body.
			responseData = httpGet.getResponseBodyAsString();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpGet.releaseConnection();
			httpClient = null;
		}

		return responseData;

	}

	/**
	 * Using POST method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPost(String url, String queryString) throws Exception {
		String responseData = null;
		HttpClient httpClient = new HttpClient();

		log.info("httpPost[1] url:" + url);
		PostMethod httpPost = new PostMethod(url);
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter("http.socket.timeout",
				new Integer(CONNECTION_TIMEOUT));
		if (queryString != null && !queryString.equals("")) {
			httpPost.setRequestEntity(new ByteArrayRequestEntity(queryString
					.getBytes()));
		}

		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: "
						+ httpPost.getStatusLine());
			}
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

	/**
	 * Using POST method with multiParts.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @param files
	 *            The list of image files
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPostWithFile(String url, String queryString,
			List<FormParamter> files) throws Exception {

		String responseData = null;
		url += '?' + queryString;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		try {
			List<FormParamter> listParams = HttpUtil
					.getQueryParameters(queryString);
			int length = listParams.size() + (files == null ? 0 : files.size());
			Part[] parts = new Part[length];
			int i = 0;
			for (FormParamter param : listParams) {
				parts[i++] = new StringPart(param.getName(), HttpUtil
						.formParamDecode(param.getValue()), "UTF-8");
			}

			for (FormParamter param : files) {
				String filePath = param.getValue();
				File file = new File(filePath);
				String name = param.getName();
				// String fileName = file.getName();
				// String type = QHttpUtil.getContentType(file);
				// image/jpeg - image/png
				parts[i++] = new FilePart(name, file, "image/jpeg", "utf-8");
			}

			httpPost.setRequestEntity(new MultipartRequestEntity(parts,
					httpPost.getParams()));

			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: "
						+ httpPost.getStatusLine());
			}
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

}
