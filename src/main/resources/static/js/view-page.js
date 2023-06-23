const allReplyBtns = document.querySelectorAll(".comment-reply-link");

allReplyBtns.forEach((btn, index) =>
    btn.addEventListener("click", (e) => {
        e.preventDefault();
        const formDiv = document.querySelectorAll(".add-reply")[index];
        if (formDiv.style.display === "none") {
            formDiv.style.display = "block";
        } else {
            formDiv.style.display = "none";
        }
    })
);

const postUpVote = document.getElementById("post-up");
const postDownVote = document.getElementById("post-down");
const upForm = document.getElementById("up-form");
const downForm = document.getElementById("down-form");
postUpVote.addEventListener("click", () => {
    console.log("hello");
    upForm.submit();
});

postDownVote.addEventListener("click", () => {
    console.log("hello");
    downForm.submit();
});
