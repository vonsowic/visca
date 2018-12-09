import com.google.gson.Gson;

import static spark.Spark.*;

public class WebApp {

    public static void main(String[] args) {
        externalStaticFileLocation("/home/sawim/java/visca/src/main/resources");
        port(3000);
        get("/", (req, res) -> {
            res.type("text/html");
            res.redirect("index.html");
            return null;
        });

        before((req, res) -> {
            res.header("Content-Type", "text/plain");
        });

        post("/Address", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.address(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/ClearAll", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.clearAll(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/GetPanTiltMaxSpeed", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.getPanTiltMaxSpeed(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/PanTiltAbsolutePos", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.panTiltAbsolutePos(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/PanTiltDown", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.panTiltDown(request.panSpeed, request.tiltSpeed, Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/PanTiltHome", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.panTiltHome(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/PanTiltLeft", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.panTiltLeft(request.panSpeed, request.tiltSpeed, Byte.valueOf(request.camera));
            System.out.println(Common.readResponse());
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/PanTiltRigth", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.panTiltRight(request.panSpeed, request.tiltSpeed, Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/PanTiltUp", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.sendPanTiltUp(request.panSpeed, request.tiltSpeed, Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/ZoomTeleStd", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.sendZoomTeleStd(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        post("/ZoomWideStd", (req, res) -> {
            Gson gson = new Gson();
            ViscaRequest request = gson.fromJson(req.body(), ViscaRequest.class);
            Common.sendZoomWideStd(Byte.valueOf(request.camera));
            String resposne =Common.readResponse();
            System.out.println(resposne);
            return resposne;
        });
        put("/port", (req, res) -> {
            Common.setPort(req.queryParams("value"));
            return "";
        });
    }

    class ViscaRequest {
        String panSpeed;
        String tiltSpeed;
        String camera;
    }
}
