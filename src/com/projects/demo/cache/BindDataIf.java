package com.projects.demo.cache;

import android.content.Context;

/**
 *
 * @author wei feng
 * @date 2012-5-23
 *
 */
public interface BindDataIf {
    
    /**
     * @Description: 
     *  some operate need executing long times on background(e.g : get xml file)
     *  show progress dialog when during download,
     *  if loaded, call back the result via callback method for invoker .
     *
     * @author wei feng
     * @date 2012-5-23
     * @version 
     *
     */
    public interface Callback {
        
        /**
         * normal, not to invoke main thread (UI thread)
         * @param obj
         */
        public void callback(BindHolder holder);
    }
    
    public class BindHolder{
        
        public static final int TYPE_IMAGE = 0X0001;
        public static final int TYPE_JSON = 0X0002;
        public static final int TYPE_XML = 0X0003;
        
        private int type;
        private String url;
        private String saveFileName;
        private Object resource;
        
        public BindHolder(int type, String url) {
			this.type = type;
			this.url = url;
		}
        
        public BindHolder(int type, String url, String saveFileName) {
			this.type = type;
			this.url = url;
			this.saveFileName = saveFileName;
		}

		public int getType() {
            return type;
        }
       
        public void setType(int type) {
            this.type = type;
        }
       
        public String getUrl() {
            return url;
        }
       
        public void setUrl(String url) {
            this.url = url;
        }

		public Object getResource() {
			return resource;
		}

		public void setResource(Object resource) {
			this.resource = resource;
		}

		public String getSaveFileName() {
			return saveFileName;
		}

		public void setSaveFileName(String saveFileName) {
			this.saveFileName = saveFileName;
		}
    }
    
    public void bindData(Context context, BindHolder holder, Callback callback, boolean useNewThread);
}
