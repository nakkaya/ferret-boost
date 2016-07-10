(defnative exists [f]
  (on "defined FERRET_STD_LIB"
      ("boost/filesystem.hpp")
      "__result = obj<Boolean>(boost::filesystem::exists(f.cast<String>()->to_string()));"))
