(defnative split [s re]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string st = string::to<std::string>(s);
       std::vector<std::string> st_vec;
       boost::split(st_vec, st, boost::is_any_of(string::to<std::string>(re)));
       for (unsigned i = st_vec.size(); i-- > 0; )
         __result = runtime::cons(obj<string>(st_vec.at(i)), __result);"))

(defnative replace [s match replacement]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string ret_val = string::to<std::string>(s);
       boost::replace_all(ret_val, 
                          string::to<std::string>(match), 
                          string::to<std::string>(replacement));
       __result = obj<string>(ret_val);"))

(defnative trim [s]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "std::string val = string::to<std::string>(s);
       std::string left = boost::trim_left_copy(val);
       std::string right = boost::trim_right_copy(left);
       __result = obj<string>(right);"))

(defn concat
  ([] "")
  ([x] x)
  ([h v]
   "std::string _h = string::to<std::string>(h);
    std::string _v = string::to<std::string>(v);
   __result = obj<string>((_h + _v));")
  ([x y & more]
   (reduce concat (concat x y) more)))

(defn contains? [s x]
  "std::size_t found = string::to<std::string>(s).find(string::to<std::string>(x));
   if (found != std::string::npos)
     __result = obj<boolean>(true)")
