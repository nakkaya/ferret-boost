class serial_o : public object_t {
  boost::asio::io_service io;
  boost::asio::serial_port port;

 public:
  size_t type() const { return runtime::type::serial_o; }

  bool equals(var o) const {
    return obj<boolean>(this == o.cast<serial_o>());
  }

#if !defined(FERRET_DISABLE_STD_OUT)
  var stream_console() const {
    runtime::print("serial_o");
    return nil();
  }
#endif

  explicit serial_o() : port(io) {}
  boost::asio::serial_port &serial_port() { return port; }
  boost::asio::io_service  &io_service()  { return io; }
};
