async function sendDataEditUser(user) {
    await fetch("/admin" ,
        {method:"PUT", headers: {'Content-type': 'application/json'}, body: JSON.stringify(user)} )
}

async function fillEditModal() {
    const modalEdit = document.getElementById("editModal");
    await fillModal(modalEdit);
}