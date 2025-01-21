package com.honeywell.aidc;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonUtil {
   JsonUtil() {
      super();
   }

   static JSONObject mapToJson(Map<?, ?> data) {
      JSONObject object = new JSONObject();

      for (Entry<?, ?> entry : data.entrySet()) {
         String key = (String)entry.getKey();
         if (key == null) {
            throw new NullPointerException("key == null");
         }

         try {
            object.put(key, wrap(entry.getValue()));
         } catch (JSONException var6) {
            var6.printStackTrace();
         }
      }

      return object;
   }

   static JSONArray collectionToJson(Collection<?> data) {
      JSONArray jsonArray = new JSONArray();
      if (data != null) {
         for (Object aData : data) {
            jsonArray.put(wrap(aData));
         }
      }

      return jsonArray;
   }

   static JSONArray arrayToJson(Object data) throws JSONException {
      if (!data.getClass().isArray()) {
         throw new JSONException("Not a primitive data: " + data.getClass());
      } else {
         int length = Array.getLength(data);
         JSONArray jsonArray = new JSONArray();

         for (int i = 0; i < length; i++) {
            jsonArray.put(wrap(Array.get(data, i)));
         }

         return jsonArray;
      }
   }

   private static Object wrap(Object o) {
      if (o == null) {
         return null;
      } else if (!(o instanceof JSONArray) && !(o instanceof JSONObject)) {
         try {
            if (o instanceof Collection) {
               return collectionToJson((Collection<?>)o);
            }

            if (o.getClass().isArray()) {
               return arrayToJson(o);
            }

            if (o instanceof Map) {
               return mapToJson((Map<?, ?>)o);
            }

            if (o instanceof Boolean
               || o instanceof Byte
               || o instanceof Character
               || o instanceof Double
               || o instanceof Float
               || o instanceof Integer
               || o instanceof Long
               || o instanceof Short
               || o instanceof String) {
               return o;
            }

            if (o.getClass().getPackage().getName().startsWith("java.")) {
               return o.toString();
            }
         } catch (Exception var2) {
            var2.printStackTrace();
         }

         return null;
      } else {
         return o;
      }
   }
}
