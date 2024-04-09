async function deleteUserData(userId){
    await fetch(`/admin/${userId}`, {method: 'DELETE'});
}

async function fillDeleteModal() {
    const modalDelete = document.getElementById("deleteModal");
    await fillModal(modalDelete);
}

const formDelete = document.getElementById("modalBodyDelete");
formDelete.addEventListener("submit", async function(event) {
    event.preventDefault();

    const userId = event.target.querySelector("#idDelete").value;
    await deleteUserData(userId);
    await fillTableOfAllUsers();

    const deleteModal = document.getElementById('deleteModal');
    const modalBootstrap = bootstrap.Modal.getInstance(deleteModal);
    modalBootstrap.hide();
    }
)