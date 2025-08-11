document.addEventListener("DOMContentLoaded", () => {
  const links = document.querySelectorAll(".sidebar a");
  links.forEach(link => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      const target = link.getAttribute("data-content");
      if (target === "home") {
        window.location.href = "/home";
      } else if (target === "profile") {
        window.location.href = "/profile";
      } else if (target === "settings") {
        window.location.href = "/settings";
      } else {
        alert(`${target} ページを表示（今後実装予定）`);
        // 将来：ここでAJAXやページ切替処理を追加
      }
    });
  });
});
