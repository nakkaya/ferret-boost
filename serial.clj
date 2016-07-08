(defnative open [p b]
  (on "defined FERRET_STD_LIB"
      ("boost/asio/serial_port.hpp"
       "boost/asio.hpp")
      "var io_ptr = obj<Pointer>(new boost::asio::io_service());
       boost::asio::io_service & io = io_ptr.cast<Pointer>()->reference<boost::asio::io_service>();
       var port_ptr = obj<Pointer>(new boost::asio::serial_port(io));
       boost::asio::serial_port * port = port_ptr.cast<Pointer>()->pointer<boost::asio::serial_port>();

       // Base serial settings
       boost::asio::serial_port_base::baud_rate BAUD(b.cast<Number>()->as<int>());
       boost::asio::serial_port_base::flow_control FLOW( boost::asio::serial_port_base::flow_control::none );
       boost::asio::serial_port_base::parity PARITY( boost::asio::serial_port_base::parity::none );
       boost::asio::serial_port_base::stop_bits STOP( boost::asio::serial_port_base::stop_bits::one );

       try { port->open(p.cast<String>()->to_string()); } catch(const std::exception& e) { return nil(); }

       // Setup port - base settings
       port->set_option( BAUD );
       port->set_option( FLOW );
       port->set_option( PARITY );
       port->set_option( STOP );

       __result = runtime::list(port_ptr,io_ptr);"))

(defnative write [[port-ptr] byte]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port & port = port_ptr.cast<Pointer>()->reference<boost::asio::serial_port>();
       unsigned char ch = byte.cast<Number>()->as<int>();
       try { boost::asio::write(port, boost::asio::buffer(&ch, 1)); } 
       catch(const std::exception& e) { return nil(); }
       __result = byte;"))

(defnative read [[port-ptr]]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port & port = port_ptr.cast<Pointer>()->reference<boost::asio::serial_port>();
       unsigned char ch;
       try { boost::asio::read(port, boost::asio::buffer(&ch, 1)); } catch(const std::exception& e) { return nil(); }
       __result = obj<Number>((ferret::number_t)ch);"))

(defnative read-line [[port-ptr]]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port & port = port_ptr.cast<Pointer>()->reference<boost::asio::serial_port>();
       boost::asio::streambuf b;
       std::ostringstream line;
       try { boost::asio::read_until(port, b, \"\\r\\n\"); } catch(const std::exception& e) { return nil(); }
       line << &b;
       __result = obj<String>(line.str().c_str());"))

(defnative close [[port io]]
  (on "defined FERRET_STD_LIB"
      "boost::asio::serial_port & p = port.cast<Pointer>()->reference<boost::asio::serial_port>();
       p.cancel();
       p.close();
       boost::asio::io_service & i = io.cast<Pointer>()->reference<boost::asio::io_service>();
       i.stop();
       i.reset();
       delete port.cast<Pointer>()->pointer<boost::asio::serial_port>();;
       delete io.cast<Pointer>()->pointer<boost::asio::io_service>();"))
