(native-header "boost/asio/serial_port.hpp"
               "boost/asio.hpp")

(defobject serial_o "ferret-boost/serial_o.h")

(defnative open [p b]
  (on "defined FERRET_STD_LIB"
      "__result  = obj<serial_o>();

       // Base serial settings
       boost::asio::serial_port_base::baud_rate BAUD(number::to<int>(b));
       boost::asio::serial_port_base::flow_control FLOW( boost::asio::serial_port_base::flow_control::none );
       boost::asio::serial_port_base::parity PARITY( boost::asio::serial_port_base::parity::none );
       boost::asio::serial_port_base::stop_bits STOP( boost::asio::serial_port_base::stop_bits::one );

       try { 
         __result.cast<serial_o>()->serial_port().open(string::to<std::string>(p)); 
       } catch(const std::exception& e) { 
         return nil(); 
       }

       // Setup port - base settings
       __result.cast<serial_o>()->serial_port().set_option( BAUD );
       __result.cast<serial_o>()->serial_port().set_option( FLOW );
       __result.cast<serial_o>()->serial_port().set_option( PARITY );
       __result.cast<serial_o>()->serial_port().set_option( STOP );"))

(defnative write [conn byte]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port& port = conn.cast<serial_o>()->serial_port();
       unsigned char ch = number::to<int>(byte);
       try { boost::asio::write(port, boost::asio::buffer(&ch, 1)); } 
       catch(const std::exception& e) { return nil(); }
       __result = byte;"))

(defnative read [conn]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port& port = conn.cast<serial_o>()->serial_port();
       unsigned char ch;
       try { boost::asio::read(port, boost::asio::buffer(&ch, 1)); } catch(const std::exception& e) { return nil(); }
       __result = obj<number>(ch);"))

(defnative read-line [conn]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "boost::asio::serial_port& port = conn.cast<serial_o>()->serial_port();
       boost::asio::streambuf b;
       std::ostringstream line;
       try { boost::asio::read_until(port, b, \"\\r\\n\"); } catch(const std::exception& e) { return nil(); }
       line << &b;
       std::string ret_val = line.str();
       boost::replace_all(ret_val,\"\\r\\n\", \"\");
       __result = obj<string>(ret_val);"))

(defnative close [[port io]]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port& port = conn.cast<serial_o>()->serial_port();
       port.cancel();
       port.close();
       boost::asio::io_service& io = conn.cast<serial_o>()->io_service();
       io.stop();
       io.reset();"))
