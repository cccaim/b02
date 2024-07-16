async function uploadToServer (formObj){
    console.log("서버에 업로드 중입니다...")
    console.log(formObj)

    const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers: { 'Content-Type': 'multipart/from-data',
        },
    })
    return response.data
}

async function removeFileToServer(uuid, fileName){
    const response = await axios.delete(`/remove/${uuid}_${fileName}`)

    return response.data
}