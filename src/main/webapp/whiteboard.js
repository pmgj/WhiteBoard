class GUI {
    constructor() {
        this.canvas = document.getElementById("myCanvas");
        this.context = this.canvas.getContext("2d");
        this.websocket = new WebSocket(`ws://${document.location.host}${document.location.pathname}whiteboard`);
        this.websocket.onmessage = this.onMessage.bind(this);
        this.websocket.onerror = this.onError.bind(this);
    }
    onMessage(evt) {
        if (typeof evt.data === "string") {
            this.drawImageText(JSON.parse(evt.data));
        } else {
            this.drawImageBinary(evt.data);
        }
    }
    writeToScreen(message) {
        let output = document.getElementById("output");
        output.innerHTML += message + "<br>";
    }
    onError(evt) {
        this.writeToScreen(`<span style="color: red;">ERROR:</span> ${evt.data}`);
    }
    
    init() {
        this.canvas.addEventListener("click", this.defineImage.bind(this), false);
        let form = document.inputForm;
        form.onsubmit = this.defineImageBinary.bind(this);
    }
    getCurrentPos(evt) {
        let rect = this.canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    }
    defineImage(evt) {
        let currentPos = this.getCurrentPos(evt), color, shape;
        for (let i = 0; i < document.inputForm.color.length; i++) {
            if (document.inputForm.color[i].checked) {
                color = document.inputForm.color[i];
                break;
            }
        }
        for (let i = 0; i < document.inputForm.shape.length; i++) {
            if (document.inputForm.shape[i].checked) {
                shape = document.inputForm.shape[i];
                break;
            }
        }
        let obj = {
            shape: shape.value,
            color: color.value,
            coords: {
                x: currentPos.x,
                y: currentPos.y
            }
        };
        this.drawImageText(obj);
        if (document.getElementById("instant").checked) {
            this.websocket.send(JSON.stringify(obj));
        }
    }
    drawImageText(obj) {
        this.context.fillStyle = obj.color;
        switch (obj.shape) {
            case "circle":
                this.context.beginPath();
                this.context.arc(obj.coords.x, obj.coords.y, 5, 0, 2 * Math.PI, false);
                this.context.fill();
                break;
            case "square":
            default:
                this.context.fillRect(obj.coords.x, obj.coords.y, 10, 10);
                break;
        }
    }
    drawImageBinary(blob) {
        let bytes = new Uint8Array(blob);
        let imageData = this.context.createImageData(canvas.width, canvas.height);
        for (let i = 8; i < imageData.data.length; i++) {
            imageData.data[i] = bytes[i];
        }
        this.context.putImageData(imageData, 0, 0);
        let img = document.createElement('img');
        img.height = canvas.height;
        img.width = canvas.width;
        img.src = canvas.toDataURL();
    }
    defineImageBinary(evt) {
        evt.preventDefault();
        let image = this.context.getImageData(0, 0, this.canvas.width, this.canvas.height);
        let buffer = new ArrayBuffer(image.data.length);
        let bytes = new Uint8Array(buffer);
        for (let i = 0; i < bytes.length; i++) {
            bytes[i] = image.data[i];
        }
        this.websocket.send(buffer);
    }
}
let gui = new GUI();
gui.init();