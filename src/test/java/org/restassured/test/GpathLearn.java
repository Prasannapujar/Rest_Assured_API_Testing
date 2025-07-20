package org.restassured.test;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GpathLearn {

    String Jsonpath= """
            {
              "id": 4,
              "name": "gold",
              "names": [
                {
                  "language": {
                    "name": "ja-Hrkt",
                    "url": "https://pokeapi.co/api/v2/language/1/"
                  },
                  "name": "金"
                },
                {
                  "language": {
                    "name": "ko",
                    "url": "https://pokeapi.co/api/v2/language/3/"
                  },
                  "name": "골드"
                },
                {
                  "language": {
                    "name": "fr",
                    "url": "https://pokeapi.co/api/v2/language/5/"
                  },
                  "name": "Or"
                },
                {
                  "language": {
                    "name": "de",
                    "url": "https://pokeapi.co/api/v2/language/6/"
                  },
                  "name": "Gold"
                },
                {
                  "language": {
                    "name": "es",
                    "url": "https://pokeapi.co/api/v2/language/7/"
                  },
                  "name": "Oro"
                },
                {
                  "language": {
                    "name": "it",
                    "url": "https://pokeapi.co/api/v2/language/8/"
                  },
                  "name": "Oro"
                },
                {
                  "language": {
                    "name": "en",
                    "url": "https://pokeapi.co/api/v2/language/9/"
                  },
                  "name": "Gold"
                }
              ],
              "version_group": {
                "name": "gold-silver",
                "url": "https://pokeapi.co/api/v2/version-group/3/"
              }
            }
            
            """;

   @Test
    public void gpath() {
        // Extracting the name of the version group
        JsonPath paths = JsonPath.from(Jsonpath);
      List<Map<Object, Object>> languages = paths.getList("names.language");
        System.out.println(languages);
        // Extract the url form the map
        List<String> urls = paths.getList("names.language.url");
       System.out.println(urls);

       // get the name from names array
         List<String> names = paths.getList("names.name");
       System.out.println(names);

       // find all the url for where name is de
         List<Map<String,String>> deUrls = paths.getList("names.language.findAll { it.name == 'de' }");
       System.out.println(deUrls);

       List<String> deurl = paths.getList("names.language.findAll { it.name == 'de' }.url");
       System.out.println(deurl);
    }

}
