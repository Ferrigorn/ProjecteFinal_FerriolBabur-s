/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function() {
    const libroForm = document.getElementById('formLibro');
    
    const userName = sessionStorage.getItem('nom');
    
    if (userName) {
        document.getElementById('userName').textContent = userName;
    } else {
        window.location.href = '/index.html';
    }

     /*Per a crear llibre*/
     document.getElementById('formLibro').addEventListener('submit', async function(event) {
        event.preventDefault();
        
        const form = document.getElementById('formLibro');
        const formData = new FormData(form);
    
        for (const pair of formData.entries()) {
            console.log(`${pair[0]}: ${pair[1]}`);
        }
    
        try {
            const response = await fetch('/llibres/crear', {
                method: 'POST',
                body: formData
            });
    
            if (!response.ok) {
                throw new Error(`Error: ${response.statusText}`);
            }
    
            const data = await response.json();
            console.log('Success:', data);
            alert('Llibre creat i afegit a la teva Base de Dades');
            form.reset();
        } catch (error) {
            console.error('Error:', error);
            alert('Hi ha hagut algun problema al crear el registre del llibre');
        }
    });
    
    //Per pujar imatge a un llibre
    
    document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const titolUpdate = document.getElementById('titolUpdate').value.trim();
    const imatgeUpdate = document.getElementById('imatgeUpdate').files[0];

    if (!titolUpdate || !imatgeUpdate) {
        alert('Siusplau, omple tots els camps');
        return;
    }

    const formData = new FormData();
    formData.append('titol', titolUpdate);
    formData.append('imatge', imatgeUpdate);

    fetch('/llibres/actualizarimatge', {
        method: 'PUT',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            if (response.status === 404) {
                throw new Error('No hem trobat el llibre');
            }
            throw new Error('Error al pujar la imatge');
        }
        return response.json();
    })
    .then(data => {
        alert('Imatge pujada amb èxit');
        console.log('Libro actualizado:', data);
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
});
});
