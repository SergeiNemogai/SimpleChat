'use strict';

let stompClient = null;
let username = null;
let usernamePage = document.querySelector('#username-page');
let chatPage = document.querySelector('#chat-page');
let usernameInput = document.getElementById('username');
let messageInput = document.querySelector('#message');
let messageArea = document.querySelector('#message-area');
let usernameForm = document.querySelector('#username-form');
let messageForm = document.querySelector('#chat-form');

function connect(event) {
    username = usernameInput.value.trim();
    if(username) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat', onMessageReceived);
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');
    // Tell your username to the server
    stompClient.send("/app/login",
        {},
        JSON.stringify({username: username, text: ''})
    );
}

function onError() {
    messageArea.append("<li>Error!</li>")
}

function sendMessage(event) {
    let messageContent = document.querySelector('#message').value.trim();
    if(messageContent && stompClient) {
        let message = {
            username: username,
            text: messageContent
        };
        stompClient.send("/app/chat", {}, JSON.stringify(message));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    let serverMessages = JSON.parse(payload.body);
    for (let i = 0; i < serverMessages.messages.length; i++) {
        let message = serverMessages.messages[i];
        let li = document.createElement("li");
        li.appendChild(document.createTextNode(message.text + " (" + message.username + ")"));
        messageArea.appendChild(li);
    }
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
