package services;

import server.ServerException;

public interface Service{
    String  execute() throws ServerException;
}
