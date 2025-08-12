document.addEventListener('DOMContentLoaded', () => {
  const breadcrumb = document.getElementById('breadcrumb');

  breadcrumb.addEventListener('click', e => {
    const item = e.target.closest('.breadcrumb-item');
    if (!item) return;
    const action = item.getAttribute('data-action');
    
    if (action === 'home') {
      window.location.href = '/home';
    }
    if (action === 'department') {
      const deptId = item.getAttribute('data-id');
      // 部署ページに戻る（home画面で部署を選択した状態）
      window.location.href = `/home?dept=${deptId}`;
    }
    // team自身は何もしない（現在のページ）
  });
});
