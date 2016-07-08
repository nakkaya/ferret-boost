(defnative split [s re]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string st = s.cast<String>()->to_string();
       std::vector<std::string> st_vec;
       boost::split(st_vec, st, boost::is_any_of(re.cast<String>()->to_string()));
       for (unsigned i = st_vec.size(); i-- > 0; )
         __result = runtime::cons(obj<String>(st_vec.at(i).c_str()), __result);"))

(defnative replace [s match replacement]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string ret_val = s.cast<String>()->to_string();
       boost::replace_all(ret_val, 
                          match.cast<String>()->to_string(), 
                          replacement.cast<String>()->to_string());
       __result = obj<String>(ret_val.c_str());"))

(defnative trim [s]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string val = s.cast<String>()->to_string();
       std::string left = boost::trim_left_copy(val);
       std::string right = boost::trim_right_copy(left);
       __result = obj<String>(right.c_str());"))
