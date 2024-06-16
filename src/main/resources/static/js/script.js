/* @author Ferriol Baburés */ 

document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('formUser');
    const llistaLlibres = document.getElementById('llistaLlibres');
    const buscadorGeneral = document.getElementById('buscadorGeneral');
    const botoBuscar = document.getElementById('botoBuscar');
    
    
    /*Per crear Usuari*/
    form.addEventListener('submit', function(event){
        event.preventDefault();
        
        const nom = document.getElementById('nombreUsuario').value;
        const cognoms = document.getElementById('cognomsUsuario').value;
        const contrasenya = document.getElementById('contraseUsuario').value;
        const email = document.getElementById('emailUsuario').value;
        
        fetch('/usuaris/registre', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({nom: nom, cognoms: cognoms, contrasenya: contrasenya, email: email })
            
        })
        .then(response => {
            return response.text().then(text => {
                try {
                    return JSON.parse(text);
                } catch {
                    return text;
                }
            });
        })
        .then(data => {
            if (typeof data === 'string') {
                console.log('Success:', data);
                alert("Usuari " + nom + " s'ha creat correctament. Benvingut@ " + nom + " " + cognoms + "!");
                window.location.href = '/login.html';
            } else {
                console.log('Success:', data);
                alert("Usuari " + nom + " s'ha creat correctament. Benvingut@ " + nom + " " + cognoms + "!");
                window.location.href = '/login.html';
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    });

    
    
    
    /*Per buscar llibres per titol o autor*/

    botoBuscar.addEventListener('click', function() {
        const query = buscadorGeneral.value.trim(); // Obtiene el valor del campo y elimina espacios en blanco

        if (query === "") {
            alert("Si no poses res no ho podem buscar...😋");
            return;
        }

        // Primero, buscar por autor
        fetch(`/llibres/autor/${query}`)
            .then(response => {
                if (!response.ok) {
                    // Si no troba per autor, busca per titol
                    return fetch(`/llibres/llibre/${query}`).then(response => {
                        if (response.ok) {
                            return response.json().then(data => ({ isList: false, data }));
                        } else {
                            throw new Error("No s'han trobat resultats");
                        }
                    });
                }
                return response.json().then(data => ({ isList: true, data }));
            })
            .then(result => {
                if (result.isList) {
                    displayLlibres(result.data);
                } else {
                    displayLibro(result.data);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message);
            });
    });

    function displayLlibres(llibres) {
        // Limpia la lista de libros
        llistaLlibres.innerHTML = '';

        if (llibres.length === 0) {
            llistaLlibres.innerHTML = '<p>No hem trobat llibres que coincideixin amb la teva busca.</p>';
            return;
        }

        // Crear elements per cada llibre trobat
        

        llibres.forEach(llibre => {
            const llibreItem = document.createElement('div');
            llibreItem.className = 'llibre-item';

            const card = document.createElement('div');
            card.className = 'card';

            const cardFront = document.createElement('div');
            cardFront.className = 'card-face card-front';
            cardFront.innerHTML = `
                <h3>${llibre.titol}</h3>
                <p>Autor: ${llibre.autor}</p>
                <img src="${llibre.imatgeBase64 ? 'data:image/jpeg;base64,' + llibre.imatgeBase64 : '../Imatges/llibrePerDefecte.jpg'}" alt="${llibre.titol}" class="cardImg" />
            `;

            const cardBack = document.createElement('div');
            cardBack.className = 'card-face card-back';
            cardBack.innerHTML = `
                <p>Editorial: ${llibre.editorial}</p>
                <p>Any d'Edició: ${llibre.anyEdicio}</p>
                <p>Gènere: ${llibre.genere}</p>
                <p>Ubicació: ${llibre.ubicacio}</p>
                <p>Idioma: ${llibre.idioma}</p>
                <p>Estat: ${llibre.estat}</p>
                <p>Col·lecció: ${llibre.coleccio}</p>
                <div class="button-container">
                    <button class="back-button">Girar</button>
                </div>
            `;

            card.appendChild(cardFront);
            card.appendChild(cardBack);
            llibreItem.appendChild(card);

            const buttonContainer = document.createElement('div');
            buttonContainer.className = 'button-container';
            buttonContainer.innerHTML = `
                
                <button class="info-button">Detalls</button>
            `;
            llibreItem.appendChild(buttonContainer);

            llistaLlibres.appendChild(llibreItem);

            
            const infoButton = buttonContainer.querySelector('.info-button');
            const backButton = cardBack.querySelector('.back-button');

            

            infoButton.addEventListener('click', () => {
                card.classList.add('flipped');
                buttonContainer.classList.add('hidden');
            });

            backButton.addEventListener('click', () => {
                card.classList.remove('flipped');
                buttonContainer.classList.remove('hidden');
            });
        });
    }

    function displayLibro(llibre) {
        // Limpia la lista de libros
        llistaLlibres.innerHTML = '';

        if (!llibre) {
            llistaLlibres.innerHTML = '<p>No hem trobat el llibre</p>';
            return;
        }

        // Crear elementos para el libro encontrado
        const llibreItem = document.createElement('div');
            llibreItem.className = 'llibre-item';

            const card = document.createElement('div');
            card.className = 'card';

            const cardFront = document.createElement('div');
            cardFront.className = 'card-face card-front';
            cardFront.innerHTML = `
                <h3>${llibre.titol}</h3>
                <p>Autor: ${llibre.autor}</p>
                <img src="${llibre.imatgeBase64 ? 'data:image/jpeg;base64,' + llibre.imatgeBase64 : '../Imatges/llibrePerDefecte.jpg'}" alt="${llibre.titol}" class="cardImg" />
            `;

            const cardBack = document.createElement('div');
            cardBack.className = 'card-face card-back';
            cardBack.innerHTML = `
                <p>Editorial: ${llibre.editorial}</p>
                <p>Any d'Edició: ${llibre.anyEdicio}</p>
                <p>Gènere: ${llibre.genere}</p>
                <p>Ubicació: ${llibre.ubicacio}</p>
                <p>Idioma: ${llibre.idioma}</p>
                <p>Estat: ${llibre.estat}</p>
                <p>Col·lecció: ${llibre.coleccio}</p>
                <div class="button-container">
                    <button class="back-button">Girar</button>
                </div>
            `;

            card.appendChild(cardFront);
            card.appendChild(cardBack);
            llibreItem.appendChild(card);

            const buttonContainer = document.createElement('div');
            buttonContainer.className = 'button-container';
            buttonContainer.innerHTML = `
                <button class="read-button">Marcar Llegit</button>
                <button class="info-button">Detalls</button>
            `;
            llibreItem.appendChild(buttonContainer);

            llistaLlibres.appendChild(llibreItem);

            const readButton = buttonContainer.querySelector('.read-button');
            const infoButton = buttonContainer.querySelector('.info-button');
            const backButton = cardBack.querySelector('.back-button');

            readButton.addEventListener('click', () => {
                alert(`Marcat com llegit: ${llibre.titol}`);
            });

            infoButton.addEventListener('click', () => {
                card.classList.add('flipped');
            });

            backButton.addEventListener('click', () => {
                card.classList.remove('flipped');
            });
    }
});
    
    
    
    

    






