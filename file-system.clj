(defnative exists [f]
  (on "defined FERRET_STD_LIB"
      ("boost/filesystem.hpp")
      "__result = obj<boolean>(boost::filesystem::exists(string::to<std::string>(f)));"))
