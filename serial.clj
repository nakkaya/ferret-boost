(defobject SerialState
  (data "boost::asio::io_service io;"
        "boost::asio::serial_port port;")
  (equals "return obj<Boolean>(this == o.cast<SerialState>());")
  (stream_console "fprintf(FERRET_STD_OUT, \"SerialState\"); return nil();")
  (fns
   ("explicit SerialState() : port(io) { }")
   ("boost::asio::serial_port& serial_port() { return port;}")
   ("boost::asio::io_service& io_service() { return io;}"))
  (force-type))

(defnative open [p b]
  (on "defined FERRET_STD_LIB"
      ("boost/asio/serial_port.hpp"
       "boost/asio.hpp")
      "__result  = obj<SerialState>();

       // Base serial settings
       boost::asio::serial_port_base::baud_rate BAUD(b.cast<Number>()->as<int>());
       boost::asio::serial_port_base::flow_control FLOW( boost::asio::serial_port_base::flow_control::none );
       boost::asio::serial_port_base::parity PARITY( boost::asio::serial_port_base::parity::none );
       boost::asio::serial_port_base::stop_bits STOP( boost::asio::serial_port_base::stop_bits::one );

       try { 
         __result.cast<SerialState>()->serial_port().open(p.cast<String>()->to_string()); 
       } catch(const std::exception& e) { 
         return nil(); 
       }

       // Setup port - base settings
       __result.cast<SerialState>()->serial_port().set_option( BAUD );
       __result.cast<SerialState>()->serial_port().set_option( FLOW );
       __result.cast<SerialState>()->serial_port().set_option( PARITY );
       __result.cast<SerialState>()->serial_port().set_option( STOP );"))

(defnative write [conn byte]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port& port = conn.cast<SerialState>()->serial_port();
       unsigned char ch = byte.cast<Number>()->as<int>();
       try { boost::asio::write(port, boost::asio::buffer(&ch, 1)); } 
       catch(const std::exception& e) { return nil(); }
       __result = byte;"))

(defnative read [conn]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port& port = conn.cast<SerialState>()->serial_port();
       unsigned char ch;
       try { boost::asio::read(port, boost::asio::buffer(&ch, 1)); } catch(const std::exception& e) { return nil(); }
       __result = obj<Number>((ferret::number_t)ch);"))

(defnative read-line [conn]
  (on "defined FERRET_STD_LIB"
      ("boost/algorithm/string.hpp")
      "boost::asio::serial_port& port = conn.cast<SerialState>()->serial_port();
       boost::asio::streambuf b;
       std::ostringstream line;
       try { boost::asio::read_until(port, b, \"\\r\\n\"); } catch(const std::exception& e) { return nil(); }
       line << &b;
       std::string ret_val = line.str();
       boost::replace_all(ret_val,\"\\r\\n\", \"\");
       __result = obj<String>(ret_val);"))

(defnative close [[port io]]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port& port = conn.cast<SerialState>()->serial_port();
       port.cancel();
       port.close();
       boost::asio::io_service& io = conn.cast<SerialState>()->io_service();
       io.stop();
       io.reset();"))
