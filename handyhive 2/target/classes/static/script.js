function previewPost() {
    const imageInput = document.getElementById("imageInput");
    const captionInput = document.getElementById("captionInput");
    const postContainer = document.getElementById("postContainer");

    if (imageInput.files.length === 0) {
        alert("Please select an image!");
        return;
    }

    const file = imageInput.files[0];
    const reader = new FileReader();

    reader.onload = function(event) {
        const imageUrl = event.target.result; // Convert image to a data URL

        // Create new post HTML dynamically
        const newPost = `
            <div class="post">
                <img src="${imageUrl}" alt="Uploaded Image" class="post-image">
                <p class="post-caption">${captionInput.value}</p>
            </div>
        `;

        // Add the new post at the top
        postContainer.innerHTML = newPost + postContainer.innerHTML;
    };

    reader.readAsDataURL(file); // Convert the image to a base64 URL
}
