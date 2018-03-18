/* 
 * Copyright 2017 Aaron Lucia.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var stompClient = null;
var COLOR_PATTERN = /^#?([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$/i;
var FRAME_PATTERN = /^frame$/i;
var SINGLE_PATTERN = /^(\d+)=#([\da-f]{6})$/i;
var RANGE_PATTERN = /^\[(\d+)-(\d+)\]=#([\da-f]{6})$/i;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#controls").show();
    } else {
        $("#controls").hide();
    }
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            console.log(JSON.parse(greeting.body).content);
        });
    }, function (message) {
        if (message.includes("Whoops! Lost connection")) {
            disconnect();
        }
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendColor() {
    stompClient.send("/app/color", {}, JSON.stringify({
        'red': parseFloat($("#color-r").val()),
        'green': parseFloat($("#color-g").val()),
        'blue': parseFloat($("#color-b").val()),
        'alpha': 255
    }));
}

function sendEffect() {
    stompClient.send("/app/effect", {}, JSON.stringify({
        'type': '.ChristmasEffect'
    }));
}

function parseColor(colorString) {
    var colorMatch = COLOR_PATTERN.exec(colorString);
    if (colorMatch) {
        return {
            'red': parseInt(colorMatch[1], 16),
            'green': parseInt(colorMatch[2], 16),
            'blue': parseInt(colorMatch[3], 16),
            'alpha': 255
        };
    } else {
        console.log("Cannot parse color: " + colorString);
    }
}

function parseCommand(command, frames) {
    var frameMatch = FRAME_PATTERN.exec(command);
    var singleMatch = SINGLE_PATTERN.exec(command);
    var rangeMatch = RANGE_PATTERN.exec(command);
    
    var currentFrame = frames.length - 1;
    if (frameMatch) {
        frames.push({});
        console.log("Start of frame " + (frames.length - 1));
    } else if (singleMatch) {
        var location = singleMatch[1];
        var c = parseColor(singleMatch[2]);
        frames[currentFrame][location] = c;
        console.log("frames[" + currentFrame + "][" + location + "]=" + c);
    } else if (rangeMatch) {
        var from = rangeMatch[1];
        var to = rangeMatch[2];
        var c = parseColor(rangeMatch[3]);
        for (var i = from; i <= to; i++) {
            frames[currentFrame][i] = c;
        }
        console.log("frames[" + currentFrame + "][" + from + "-" + to + "]=" + c);
    } else {
        console.log("Cannot parse line: " + command);
    }
}

function sendCommands() {
    var frames = [{}];
    var lines = $('#commands').val().split('\n');
    for (var i = 0; i < lines.length; ++i) {
        var command = lines[i].replace(/\s+/g, "");
        if (command) {
            parseCommand(command, frames);
        }
    }
    console.log(frames);
    
    var sendFrames = [];
    for (var i = 0; i < frames.length; ++i) {
        sendFrames.push({
            'type': '.FrameEffect',
            'value': frames[i]
        });
    }
    
    stompClient.send("/app/animate", {}, JSON.stringify({
        'frames': sendFrames
    }));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("input[type=range]").on('change', function(){
        sendColor();
    });
    $("#christmas").click(function () {
        sendEffect();
    });
    $("#play").click(function () {
        sendCommands();
    });
    $("#upload").change(function () {
        if (!window.File || !window.FileReader || !window.FileList || !window.Blob) {
            alert('The File APIs are not fully supported in this browser.');
            return;
        }
        if (this.files[0]) {
            var file = this.files[0];
            var fr = new FileReader();
            fr.onload = function() {
                $('#commands').val(fr.result);
            };
            fr.readAsText(file);
        }
    });
    connect();
});