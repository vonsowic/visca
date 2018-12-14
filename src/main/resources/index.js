function Address() {
    post("/Address")
        .then(function (res) {
            output(res)
        })
}

function ClearAll() {
    post("/ClearAll")
        .then(function (res) {
            output(res)

        })
}

function GetPanTiltMaxSpeed() {
    post("/GetPanTiltMaxSpeed")
        .then(function (res) {
            output(res)

        })
}

function PanTiltAbsolutePos() {
    post("/PanTiltAbsolutePos")
        .then(function (res) {
            output(res)

        })
}

function PanTiltDown() {
    post("/PanTiltDown")
        .then(function (res) {
            output(res)

        })
}

function PanTiltHome() {
    post("/PanTiltHome")
        .then(function (res) {
            output(res)

        })
}

function PanTiltLeft() {
    post("/PanTiltLeft")
        .then(function (res) {
            output(res)

        })
}


function PanTiltRigth() {
    post("/PanTiltRigth")
        .then(function (res) {
            output(res)

        })
}

function PanTiltUp() {
    post("/PanTiltUp", {
    })
        .then(function (res) {
            output(res)

        })
}

function ZoomWideStd() {
    post("/ZoomWideStd")
        .then(function (res) {
            output(res)
        })
}

function ZoomTeleStd() {
    post("/ZoomTeleStd")
        .then(function (res) {
            output(res)
        })
}

function post(url) {
    var panSpeed = document.getElementById('pan').value;
    var tiltSpeed = document.getElementById('tilt').value;

    if (isNaN(panSpeed) && panSpeed) {
        alert('Pan speed is not number')
    }

    if (isNaN(tiltSpeed) && tiltSpeed) {
        alert('Tilt speed is not number')
    }

    return axios.post(url, {
        camera: document.getElementById('camera').value,
        panSpeed: panSpeed,
        tiltSpeed: tiltSpeed
    })
}

function setPort() {
    return axios.put("/port?value=" + document.getElementById("port").value)
}

function output(res) {
    console.log(res);

    document.getElementById("output").innerText += "\n" + res.data
}