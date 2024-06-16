/**
 *
 * @author Ferriol Baburés
 */
document.addEventListener("DOMContentLoaded", function() {
    const llistaLlibres = document.getElementById('llistaLlibres');
    const buscadorAutor = document.getElementById('buscadorAutor');
    const botoBuscarAutor = document.getElementById('botoBuscarAutor');
    const buscadorTitol = document.getElementById('buscadorTitol');
    const botoBuscarTitol = document.getElementById('botoBuscarTitol');
    const buscadorGenere = document.getElementById('buscadorGenere');
    const botoBuscarGenere = document.getElementById('botoBuscarGenere');
    
    
    // Buscar per autor
    botoBuscarAutor.addEventListener('click', function() {
       const query = buscadorAutor.value.trim(); // Obtiene el valor del campo y elimina espacios en blanco

    if (query === "") {
        alert("Si no poses res no ho podem buscar...😋");
        return;
    }

    
    fetch(`/llibres/autor/${query}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No hem trobat resultats coincidents");
            }
            return response.json();
        })
        .then(llibres => {
            displayLlibres(llibres);
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
            llistaLlibres.innerHTML = '<p>No hem trobat llibres que coincideixin amb la teva busca</p>';
            return;
        }

        // Crear elements para cada llibre trobat
        

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
                <img src="${llibre.imatgeBase64 ? 'data:image/jpeg;base64,' + llibre.imatgeBase64 
                : '../Imatges/llibrePerDefecte.jpg'}" alt="${llibre.titol}" class="cardImg" />
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
                <button class="read-button">Llegit</button>
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
                buttonContainer.classList.add('hidden');
            });

            backButton.addEventListener('click', () => {
                card.classList.remove('flipped');
                buttonContainer.classList.remove('hidden');
            });
        });
    };
    
    
  //Buscar per titol  
  
  botoBuscarTitol.addEventListener('click', function() {
       const query = buscadorTitol.value.trim(); 

    if (query === "") {
        alert("Si no poses res no ho podem buscar...😋");
        return;
    }

    
    fetch(`/llibres/llibre/${query}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No hem trobat resultats coincidents");
            }
            return response.json();
        })
        .then(llibres => {
            displayLlibres(llibres);
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
    });
    
   /* function displayLlibre(llibre) {
        // Limpia la lista de libros
        llistaLlibres.innerHTML = '';

        if (!llibre) {
            llistaLlibres.innerHTML = '<p>No hem trobat el llibre que busques</p>';
            return;
        }

        // Crear els elements pel llibre trobat
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
*/

//Buscar per gènere

async function cargarGeneresSelect() {
    const buscadorGenere = document.getElementById('buscadorGenere');
    buscadorGenere.innerHTML = '<option value="">Cargando...</option>';

    try {
        const response = await fetch('/llibres/generes');
        const generes = await response.json();

        buscadorGenere.innerHTML = '<option value="">Seleccionar...</option>';
        generes.forEach(genere => {
            const option = document.createElement('option');
            option.value = genere;
            option.textContent = genere;
            buscadorGenere.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error);
        buscadorGenere.innerHTML = '<option value="">Error al carregar els gèneres</option>';
    }
}

    botoBuscarGenere.addEventListener('click', async function() {
    const genereSelect = document.getElementById('buscadorGenere');
    const genere = genereSelect.value;

        if (genere === "") {
            alert("Selecciona un gènere de la llista");
            return;
        }

        try {
            const response = await fetch(`/llibres/genere/${genere}`);
            if (!response.ok) {
                throw new Error("No hem trobat llibres per aquest gènere");
            }
            const llibres = await response.json();
            displayLlibres(llibres);
        } catch (error) {
            console.error('Error:', error);
            alert(error.message);
        }
});

cargarGeneresSelect();





});