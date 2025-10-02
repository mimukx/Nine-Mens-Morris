document.addEventListener("DOMContentLoaded", function () {
    const sequences = document.querySelectorAll(".image-sequence");

    sequences.forEach(sequence => {
        const images = sequence.querySelectorAll(".step-img");
        let index = 0;

        setInterval(() => {
            images[index].classList.remove("active");
            index = (index + 1) % images.length;
            images[index].classList.add("active");
        }, 2000);
    });
});
