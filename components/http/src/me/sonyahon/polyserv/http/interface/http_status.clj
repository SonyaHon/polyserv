(ns me.sonyahon.polyserv.http.interface.http-status
  (:require [me.sonyahon.polyserv.http.status :as status]))

(def ok
  "Standard response for successful HTTP requests.
   The actual response will depend on the request method used.
   In a GET request, the response will contain an entity
   corresponding to the requested resource. In a POST request,
   the response will contain an entity describing or containing
   the result of the action." status/ok)
(def created
  "The request has been fulfilled, resulting in the
   creation of a new resource." status/created)
(def accepted
  "The request has been accepted for processing, but
   the processing has not been completed. The request
   might or might not be eventually acted upon, and may
   be disallowed when processing occurs." status/accepted)
(def no-content
  "The server successfully processed the request, and
   is not returning any content." status/no-content)
(def partial-content
  "The server is delivering only part of the resource
   (byte serving) due to a range header sent by the client.
   The range header is used by HTTP clients to enable
   resuming of interrupted downloads, or split a download
   into multiple simultaneous streams." status/partial-content)


(def bad-request
  "The server cannot or will not process the request due
   to an apparent client error (e.g. malformed request
   syntax, size too large, invalid request message framing,
   or deceptive request routing)." status/bad-request)
(def unauthorized
  "Similar to 403 Forbidden, but specifically for use when
   authentication is required and has failed or has not yet
   been provided. The response must include a WWW-Authenticate
   header field containing a challenge applicable to the
   requested resource. 401 semantically means \"unauthorised\",
   the user does not have valid authentication credentials
   for the target resource." status/unauthorized)
(def forbidden
  "The request contained valid data and was understood by
   the server, but the server is refusing action. This may
   be due to the user not having the necessary permissions
   for a resource or needing an account of some sort, or
   attempting a prohibited action (e.g. creating a duplicate
   record where only one is allowed). This code is also
   typically used if the request provided authentication by
   answering the WWW-Authenticate header field challenge, but
   the server did not accept that authentication.
   The request should not be repeated." status/forbidden)
(def not-found
  "The requested resource could not be found but may be
   available in the future. Subsequent requests by the
   client are permissible." status/not-found)

