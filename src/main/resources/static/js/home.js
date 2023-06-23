const upvoteBtns = document.querySelectorAll(".upvote");
const downvoteBtns = document.querySelectorAll(".downvote");

upvoteBtns.forEach((btn) =>
    btn.addEventListener("click", (e) => {
        console.log(e.target.id.substring(8));
        const formId = "up-form-" + e.target.id.substring(8);
        console.log(formId);
        document.getElementById(formId).submit();
    })
);

downvoteBtns.forEach((btn) =>
    btn.addEventListener("click", (e) => {
        console.log(e.target.id.substring(10));
        const formId = "down-form-" + e.target.id.substring(10);
        console.log(formId);
        document.getElementById(formId).submit();
    })
);
