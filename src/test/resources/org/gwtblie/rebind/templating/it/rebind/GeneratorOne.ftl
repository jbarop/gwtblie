package ${packageName};

import java.util.Map;
import java.util.TreeMap;

class ${className} implements ${requestedType} {
  @Override
  public Map<String, String> get() {
      Map<String, String> result = new TreeMap<String, String>();

      <#list params?keys as key>
      result.put("${key}", "${params[key]}");
      </#list>

      return result;
  }
}
