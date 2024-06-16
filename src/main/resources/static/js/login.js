/* @author Ferriol Baburés */ 
/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('loginForm').addEventListener('submit', function(event) {
            event.preventDefault(); 

            const email = document.getElementById('email').value;
            const contrasenya = document.getElementById('contrasenya').value;

            fetch('/usuaris/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: email, contrasenya: contrasenya })
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Usuari o contrasenya incorrectes, torna-ho a provar');
                }
            })
            .then(data => {
                // Guardar les dades a la sessionStorage 
                sessionStorage.setItem('email', data.email);
                sessionStorage.setItem('nom', data.nom);

                // Redirigir a la pàgina de perfil
                window.location.href = '/protected/perfil.html';
            })
            .catch(error => {
                alert('Inici de sessió fallit: ' + error.message);
            });
        });
    });

