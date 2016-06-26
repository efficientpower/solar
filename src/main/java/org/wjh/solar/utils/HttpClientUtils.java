package org.wjh.solar.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * httpClient工具类
 * 
 * @author wangjihui
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {
	
	private static Log logger = LogFactory.getLog(HttpClientUtils.class);
	
	private static volatile HttpClient httpClient = null;

	private HttpClientUtils() {

	}

	/**
	 * Download data
	 * 
	 * @param url
	 * @return bytes data
	 */
	public static byte[] downloadData(String url) {
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = getHttpClient().execute(httpGet);
			return responseToByte(response);
		} catch (Exception e) {
			logger.error("Send Get request to url faild, url: " + url, e);
		}finally{
			httpGet.abort();
		}
		return null;
	}

	/**
	 * Send get to URL.
	 * 
	 * @param url
	 * @return result
	 */
	public static String sendGet(String url) {
		return sendGet(url, "UTF-8");
	}

	/**
	 * Send get to URL.
	 * 
	 * @param url
	 * @param charset
	 * @return result
	 */
	public static String sendGet(String url, String charset) {
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = getHttpClient().execute(httpGet);
			return responseToString(response, charset);
		} catch (Exception e) {
			logger.error("Send Get request to url faild, url: " + url, e);
		} finally {
			httpGet.abort();
		}
		return null;
	}

	/**
	 * send post to URL
	 * 
	 * @param url
	 * @return
	 */
	public static String sendPost(String url) {
		return sendPost(url, "UTF-8");
	}

	/**
	 * send post to URL
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String sendPost(String url, String charset) {
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse response = getHttpClient().execute(httpPost);
			return responseToString(response, charset);
		} catch (Exception e) {
			logger.error("Send Post request to url faild, url: " + url, e);
		} finally {
			httpPost.abort();
		}
		return null;
	}

	/**
	 * send post to URL with param
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPostWithParam(String url, Map<String, String> params) {
		return sendPostWithParam(url, params, "UTF-8");
	}

	/**
	 * send post to URL with param
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String sendPostWithParam(String url, Map<String, String> params, String charset) {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			HttpResponse response = getHttpClient().execute(httpPost);
			return responseToString(response, charset);
		} catch (Exception e) {
			logger.error("Send Post request to url faild, url: " + url, e);
		} finally {
			httpPost.abort();
		}
		return null;
	}

	private static byte[] responseToByte(HttpResponse response) throws Exception {
		HttpEntity entity = getHttpEntity(response);
		if (entity == null) {
			return null;
		}
		return EntityUtils.toByteArray(entity);
	}

	private static String responseToString(HttpResponse response, String charset) throws Exception {
		HttpEntity entity = getHttpEntity(response);
		if (entity == null) {
			return null;
		}
		return EntityUtils.toString(entity, charset);
	}

	private static HttpEntity getHttpEntity(HttpResponse response) throws Exception {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			logger.error(response);
			EntityUtils.consume(response.getEntity());
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		} else {
			return response.getEntity();
		}
	}

	/**
	 * Create an HttpClient with the ThreadSafeClientConnManager.
	 * 
	 * @return
	 */
	private static HttpClient getHttpClient() {
		if (httpClient == null) {
			ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager();
			connectionManager.setDefaultMaxPerRoute(500);// 每个host最多50个连接
			connectionManager.setMaxTotal(5000); //一共5000个连接
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient(connectionManager);
			HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 500); //请求超时(ms)
			HttpConnectionParams.setSoTimeout(defaultHttpClient.getParams(), 500);//响应超时(ms)
			// 增加gzip支持
			defaultHttpClient.addRequestInterceptor(new AcceptEncodingRequestInterceptor());
			defaultHttpClient.addResponseInterceptor(new ContentEncodingResponseInterceptor());
			httpClient = defaultHttpClient;
		}
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		return httpClient;
	}

	private static class AcceptEncodingRequestInterceptor implements HttpRequestInterceptor {
		@Override
		public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
			if (!request.containsHeader("Accept-Encoding")) {
				request.addHeader("Accept-Encoding", "gzip");
			}
		}
	}

	private static class ContentEncodingResponseInterceptor implements HttpResponseInterceptor {
		public void process(final HttpResponse response, final HttpContext context) throws HttpException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				Header ceheader = entity.getContentEncoding();
				if (ceheader != null) {
					HeaderElement[] codecs = ceheader.getElements();
					for (int i = 0; i < codecs.length; i++) {
						if ("gzip".equalsIgnoreCase(codecs[i].getName())) {
							response.setEntity(new GzipDecompressingEntity(response.getEntity()));
							return;
						}
					}
				}
			}
		}
	}

	private static class GzipDecompressingEntity extends HttpEntityWrapper {
		
		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException {
			InputStream wrappedin = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedin);
		}
	}
}
