/**
 * This package provides classes and interfaces
 * that implements a command pattern for service dispatch mechanism.
 *
 * <p>
 * The services registration process is handled semi-automatically by the
 * service dispatcher, each new service <b>must be declared</b> in the
 * service.xml file using the syntax:
 * <p>
 * {@code <service class="fullQualifiedClassName" >ServiceName</service>}
 * </p>
 * The {@link services.ServiceDispatcher#load(java.lang.String)} method
 * will load and register each service, so they will be available
 * to the dispatcher at runtime.
 *
 * @author Pio Raffaele Fina
 */
package services;
