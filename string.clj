(defnative split [s re]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string st = s.to<std::string>();
       std::vector<std::string> st_vec;
       boost::split(st_vec, st, boost::is_any_of(re.to<std::string>()));
       for (unsigned i = st_vec.size(); i-- > 0; )
         __result = runtime::cons(obj<String>(st_vec.at(i)), __result);"))

(defnative replace [s match replacement]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string ret_val = s.to<std::string>();
       boost::replace_all(ret_val, 
                          match.to<std::string>(), 
                          replacement.to<std::string>());
       __result = obj<String>(ret_val);"))

(defnative trim [s]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string val = s.to<std::string>();
       std::string left = boost::trim_left_copy(val);
       std::string right = boost::trim_right_copy(left);
       __result = obj<String>(right);"))
