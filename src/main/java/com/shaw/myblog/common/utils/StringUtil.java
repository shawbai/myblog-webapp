package com.shaw.myblog.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

public class StringUtil extends org.springframework.util.StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str null、“ ”、“null”都返回true
     * @return
     */
    public static boolean isNullString(String str) {
        return (null == str || StringUtils.isBlank(str.trim()) || "null".equals(str.trim().toLowerCase()));
    }

    public static String join(String[] array) {
        if (array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (String s : array)
            sb.append(s);
        return sb.toString();
    }

    /**
     * 格式化字符串
     * 如果为空，返回“”
     *
     * @param str
     * @return
     */
    public static String formatString(String str) {
        if (isNullString(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static String formatStringTrim(String str) {
        return formatString(str).trim();
    }

    /**
     * 截取字符串，字母、汉字都可以，汉字不会截取半
     *
     * @param str 字符串
     * @param n   截取的长度，字母数，如果为汉字，一个汉字等于两个字母数
     * @return
     */
    public static String subStringByByte(String str, int n) {
        int num = 0;
        try {
            byte[] buf = str.getBytes("GBK");
            if (n >= buf.length) {
                return str;
            }
            boolean bChineseFirstHalf = false;
            for (int i = 0; i < n; i++) {
                if (buf[i] < 0 && !bChineseFirstHalf) {
                    bChineseFirstHalf = true;
                } else {
                    num++;
                    bChineseFirstHalf = false;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.substring(0, num);
    }

    /**
     * Created by zhoujun on 2014/5/30
     * 验证输入的是否是数字
     *
     * @param num 字符串数字
     * @return
     */
    public static boolean inputIsNum(final String num) {
        boolean flag = true;
        try {
            Long.valueOf(num);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean inputIsDouble(String num) {
        boolean flag = true;
        try {
            Double.valueOf(num);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 替换中间字符串
     *
     * @param str
     * @param n
     * @return
     */
    public static String replaceSubString(String str, int n) {
        String repaceStr = "";
        try {
            //前n位
            String headStr = str.substring(0, n);
            //后n位
            String lastStr = str.substring(str.length() - n, str.length());
            /*
			 * 中间的字符串替换*
			 */
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length() - 2 * n; i++) {
                sb = sb.append("*");
            }
            repaceStr = headStr + sb.toString() + lastStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repaceStr;
    }

    /**
     * @param ch
     * @param strs
     * @return
     * @说明：将字符串转成map格式,如"红色:#ff0000"转成key为"红色",值为"红色:#ff0000"
     * @author xuefeng
     * @date:2015-5-25 上午10:40:46
     */
    public static Map<String, String> subString(String ch, List<String> strs) {
        Map<String, String> retmap = new HashMap<String, String>();
        for (String oldStr : strs) {
            int index = oldStr.indexOf(ch);
            if (index != -1) {
                String newStr = oldStr.substring(0, index);
                retmap.put(newStr, oldStr);
            } else {
                retmap.put(oldStr, oldStr);
            }
        }
        return retmap;
    }

    /**
     * @param ch
     * @param strs
     * @return
     * @throws
     * @说明：将字符串按指定分隔符分隔成数组后,再进行连接(默认连接方式为空字符串,待扩展)
     * @return: String
     * @author: xuefeng
     * @2015-5-25 上午09:22:38
     */
    public static String subString(String ch, String[] strs) {
        String retStr = "";
        for (String oldStr : strs) {
            int index = oldStr.indexOf(ch);
            if (index != -1) {
                String newStr = oldStr.substring(0, index);
                retStr += newStr;
            } else {
                retStr += oldStr;
            }
        }
        return retStr;
    }

    /**
     * @param arrStr
     * @return
     * @throws
     * @说明：对字符串数组中的元素进行排序
     * @return: String[]
     * @author: xuefeng
     * @2015-5-25 上午09:21:45
     */
    public static String[] sortStringArray(String[] arrStr) {
        for (int i = 0; i < arrStr.length - 1; i++) {
            String maxStr = arrStr[i];
            int index = i;
            for (int j = i + 1; j < arrStr.length; j++) {
                if (maxStr.compareTo(arrStr[j]) < 0) {
                    maxStr = arrStr[j];
                    index = j;
                }
            }
            arrStr[index] = arrStr[i];
            arrStr[i] = maxStr;
        }
        return arrStr;
    }

    /**
     * @param array1
     * @param array2
     * @return
     * @throws
     * @说明：比较两个字符串数组是否相等
     * @return: boolean
     * @author: xuefeng
     * @2015-5-25 上午09:22:06
     */
    public static boolean equalsStringArray(String[] array1, String[] array2) {
        if (array1 == array2)
            return true;
        if (array1 == null || array2 == null)
            return false;
        if (array1.length != array2.length)
            return false;
        array1 = sortStringArray(array1);
        array2 = sortStringArray(array2);
        for (int i = 0; i < array1.length; i++) {
            if (!array1[i].equals(array2[i]))
                return false;
        }
        return true;
    }


    public static String fetchAlipayProcessId(Long id) {
        String idstr = id.toString();
        if (idstr.length() < 3) {
            int bu = 3 - idstr.length();
            for (int i = 0; i < bu; i++) {
                idstr += "0";
            }
        }
        return idstr;
    }

    /**
     * 根据规则分割字符串
     *
     * @param str
     * @param regex
     * @return
     */
    public static String splitFirstStr(String str, String regex) {
        if (StringUtil.isNullString(str)) {
            return null;
        } else {
            return str.split(regex)[0];
        }

    }

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i)).append(",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    public static List<String> stringToList(String str) {
        String[] vals = str.split(",");
        return Arrays.asList(vals);
    }
    
    /**
     * 移除数组中空的内容
     * 
     * @param list
     * @return
     */
    public static <E> List<E> removeEmptyList(List<E> list) {  
    List<E> list1 = new ArrayList<E>();  
      if(list==null||list.size()<=0)  
          return null;  
      for(int i=0;i<list.size();i++) {  
          //进入每一个list  
          E listi = list.get(i);
          for (Field f : listi.getClass().getDeclaredFields()) {
              f.setAccessible(true);
              try {
  				if (!(f.get(listi) == null)) { 
  					list1.add(listi);  
  					break;
  				}
  			} catch (IllegalArgumentException e) {
  				e.printStackTrace();
  				return list;
  			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return list;
			}
          }
      }  
      return list1;  
  }
    
    public static String longArrayToString(Long[] longArray){
		// 自定义一个字符缓冲区，  
        StringBuilder sb = new StringBuilder();  
        //遍历Long数组，并将Long数组中的元素转换成字符串储存到字符缓冲区中去  
        for(int i=0;i<longArray.length;i++){ 
        	sb.append(longArray[i]);
            if(i<longArray.length-1)  
                sb.append(",");  
        }  
        return sb.toString();
	}
	
    public static Long[] stringToLongArray(String str){
		String[] arr = str.split(",");
		Long[] longArray = new Long[arr.length];
		for(int i=0;i<arr.length;i++){
			longArray[i] = Long.valueOf(arr[i]);
		}
		return longArray;
	}
	
    public static String encodeFormate(String str){
   	 if(isNullString(str)) {  
            return "";  
        } else {  
            try {
				return URLEncoder.encode(str,"utf-8");
			} catch (UnsupportedEncodingException e) {
				 return "";  
			}  
        } 
   }
}
