const fileInput = document.getElementById("fileInput")
const fileLabel = document.getElementById("fileLabel")
const uploadButton = document.getElementById("uploadButton")

fileInput.addEventListener("change", function() {
    if (fileInput.value == "") { // para o caso do usuario nao escolher nenhum ficheiro
        return
    }

    fileLabel.textContent = fileInput.value.replace("C:\\fakepath\\", "")
})

uploadButton.addEventListener("click", async function(event) {
    event.preventDefault()

    if (uploadButton.innerHTML != "Carregar") { // verifica se ja esta esperando a resposta do servidor
        return
    }

    // cria a animacao de espera
    uploadButton.innerHTML = "<div></div>"
    uploadButton.title = "Por favor espere"
    uploadButton.style.cursor = "not-allowed"

    const formData = new FormData()
    formData.append("file", fileInput.files[0])

    const res = await fetch(document.URL + "upload", {
        method: "POST",
        body: formData
    })

    const resposta = await res.json()
    if (resposta == true) {
        // limpar o input de arquivos depoois que o arquivo e guardado
        fileInput.value = ""
        fileLabel.textContent = "Carregar ficheiro"
        update()
        uploadButton.innerHTML = "Carregar"
        uploadButton.title = ""
        uploadButton.style.cursor = "pointer"
    } else {
        alert("Ocorreu um erro ao carregar o ficheiro")
        uploadButton.innerHTML = "Carregar"
        uploadButton.title = ""
        uploadButton.style.cursor = "pointer"
    }
})

async function update() {
    const resposta = await fetch(document.URL + "getallfiles")
    const ficheiros = await resposta.json()

    // a div que tem desde a frase "Todos os ficheiros carregados", a div que mostra todos os ficheios
    const filesDiv = document.getElementById("files")

    if (ficheiros.length == 0) { // se nao tiver nenhum ficheiro
        if (filesDiv.lastChild.textContent == "Nada por enquanto...") { // verifica se a frase ja foi escrita, para nao duplicar
            return
        }

        const p = document.createElement("p")
        p.textContent = "Nada por enquanto..."
        filesDiv.appendChild(p)
        return
    }

    // a div que vai ter todos os ficheiros
    const allFilesDiv = document.getElementById("allFiles")

    // limpra antes de colocar novos itens, para evitar duplicados
    cleanFiles()
    // criando as divs que vao mostrar cada ficheiro
    const filesSize = await getFilesSize() // lista dos tamanhos
    let a = 0
    ficheiros.forEach(ficheiro => {
        const fileDiv = document.createElement("div")
        fileDiv.className = "file"

        // o p que vai ter o nome do arquivo
        const p = document.createElement("p")
        p.textContent = ficheiro
        fileDiv.appendChild(p)

        // o botao de download
        const downloadButton = document.createElement("button")
        downloadButton.textContent = "Transferir (" + convertFileSize(filesSize[a]) + ")"
        a ++
        downloadButton.addEventListener("click", function() {download(ficheiro)})
        fileDiv.appendChild(downloadButton)

        allFilesDiv.appendChild(fileDiv)
    })

    filesDiv.appendChild(allFilesDiv)
}

// funcao para baixar o arquivo, ativada ao clicar em um dos botoes de download
function download(filename) {
    const link = document.createElement("a")
    link.href = "files/" + filename

    link.download = filename // o nome do ficheiro ao transferir
    document.body.appendChild(link)

    link.click()
    document.body.removeChild(link)
}

// apaga os ficheiros que estavam sendo exibidos, para evitar duplicados
function cleanFiles() {
    const allFilesDiv = document.getElementById("allFiles")
    const files = [...allFilesDiv.children] // transforma em um array estatico
    
    files.forEach(file => {
        allFilesDiv.removeChild(file)
    })

    const filesDiv = document.getElementById("files")
    if (filesDiv.querySelector("#files p:last-of-type").textContent == "Nada por enquanto...") { // apaga a frase (se tiver)
        filesDiv.removeChild(filesDiv.querySelector("#files p:last-of-type"))    
    }
}

// devolve o tamanho dos arquivos
async function getFilesSize() {
    const resposta = await fetch(document.URL + "getfilessize")
    return await resposta.json()
}

// transforma o numero em bytes para algo mais simples de entender
function convertFileSize(bytes) {
    if (bytes >= 1073741824) {
        return parseInt((bytes / (1024 * 1024 * 1024))) + " GB"
    } else if (bytes >= 1048576) {
        return parseInt((bytes / (1024 * 1024))) + " MB"
    } else if (bytes >= 1024) {
        return parseInt((bytes / 1024)) + " KB"
    } else {
        bytes + " bytes"
    }
}