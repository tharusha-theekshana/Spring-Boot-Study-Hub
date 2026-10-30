let client;
let connected = false;

function connect() {
    const username = document.getElementById("username").value.trim();

    if (!username) {
        alert("Enter a username");
        return;
    }

    client = new StompJs.Client({
        brokerURL: "ws://localhost:8080/ws",
        reconnectDelay: 0,
        onConnect: () => {
            connected = true;
            document.getElementById("status").textContent = "Connected as " + username;

            client.subscribe("/topic/public", (frame) => {
                const msg = JSON.parse(frame.body);
                const item = document.createElement("p");
                item.textContent =
                    (msg.sender || "System") + ": " +
                    (msg.content || msg.messageType);
                document.getElementById("messages").appendChild(item);
            });

            client.publish({
                destination: "/app/chat.addUser",
                body: JSON.stringify({
                    sender: username,
                    messageType: "JOIN"
                })
            });
        },
        onStompError: (frame) => {
            document.getElementById("status").textContent =
                "STOMP error: " + frame.headers.message;
        },
        onWebSocketError: () => {
            document.getElementById("status").textContent = "WebSocket connection error";
        },
        onWebSocketClose: () => {
            connected = false;
            document.getElementById("status").textContent = "Disconnected";
        }
    });

    client.activate();
}

function sendMessage() {
    const username = document.getElementById("username").value.trim();
    const input = document.getElementById("message");
    const content = input.value.trim();

    if (!connected) {
        alert("Connect first");
        return;
    }

    if (!content) return;

    client.publish({
        destination: "/app/chat.sendMessage",
        body: JSON.stringify({
            sender: username,
            content: content,
            messageType: "CHAT"
        })
    });

    input.value = "";
}