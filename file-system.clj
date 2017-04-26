(defnative exists [f]
  (on "defined FERRET_STD_LIB"
      ("boost/filesystem.hpp")
      "__result = obj<boolean>(boost::filesystem::exists(string::to<std::string>(f)));"))

(defnative create-directory [f]
  (on "defined FERRET_STD_LIB"
      ("boost/filesystem.hpp")
      "__result = obj<boolean>(boost::filesystem::create_directory(string::to<std::string>(f)));"))

(defnative file-seq [f]
  (on "defined FERRET_STD_LIB"
      ("boost/filesystem.hpp")
      "namespace fs = boost::filesystem;
       fs::path p (string::to<std::string>(f));
       fs::recursive_directory_iterator end_itr;

       for(fs::recursive_directory_iterator itr(p); itr != end_itr; ++itr)
          __result =  runtime::cons(obj<string>(itr->path().string()),__result);"))
