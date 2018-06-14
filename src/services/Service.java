package services;

import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.ServerException;

public interface Service{
    ResponseMessage execute(RequestMessage req);
}
