(defnative write [col]
  (on "defined FERRET_STD_LIB"
      ("boost/property_tree/ptree.hpp"
       "boost/property_tree/json_parser.hpp")
      "using boost::property_tree::ptree;
       using boost::property_tree::read_json;
       using boost::property_tree::write_json;
       ptree pt;
       size_t size = runtime::count(col);
       for(size_t i = 0; i < size; i+=2){
          var key = runtime::nth(col, i);
          var val = runtime::nth(col, i+1);
          pt.put (key.cast<String>()->to_string(), val.cast<String>()->to_string());
       }
       std::ostringstream buf;
       write_json (buf, pt, false);
       std::string json = buf.str();
       __result = obj<String>(json.c_str(), (json.length() - 1));"))
