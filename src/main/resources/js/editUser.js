async function sendDataEditUser(user) {
    await fetch("/api/admin" ,
        {method:"PUT", headers: {'Content-type': 'application/json'}, body: JSON.stringify(user)} )
}

const modalEdit = document.getElementById("editModal");

async function EditModalHandler() {
    await fillModal(modalEdit);
}

const formEdit = document.getElementById("modalBodyEdit");
document.addEventListener("submit", async function(event){
    event.preventDefault();

    const rolesSelect = document.getElementById("rolesEdit");
    let roles = [ROLE_USER, ROLE_ADMIN];

    // if (rolesSelect.options.length === 0) {
    //     roles.push(ROLE_USER);
    // }

    // for (let option of rolesSelect.options) {
    //     if (option.selected.value === ROLE_USER.roleName) {
    //         roles.push(ROLE_USER);
    //     } else if (option.selected.value === ROLE_ADMIN.roleName){
    //         roles.push(ROLE_ADMIN);
    //     }
    // }

    let user = {
        id: document.getElementById("idEdit").value,
        name: document.getElementById("firstNameEdit").value,
        surname: document.getElementById("lastNameEdit").value,
        age: document.getElementById("ageEdit").value,
        email: document.getElementById("emailEdit").value,
        password: document.getElementById("passwordEdit").value,
        roles: roles
    }

    await sendDataEditUser(user);
    await fillTableOfAllUsers();

    const modalBootstrap = bootstrap.Modal.getInstance(modalEdit);
    modalBootstrap.hide();
})
