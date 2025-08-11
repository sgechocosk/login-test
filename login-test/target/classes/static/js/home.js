document.addEventListener('DOMContentLoaded', () => {
  const deptList = document.getElementById('departmentList');
  const teamList = document.getElementById('teamList');
  const homeTitle = document.getElementById('homeTitle');
  const breadcrumb = document.getElementById('breadcrumb');
  const newTeamForm = document.getElementById('newTeamForm');

  const companyName = homeTitle.textContent.trim();

  // 部署クリック処理
  deptList.addEventListener('click', e => {
    const box = e.target.closest('.dept-box');
    if (!box) return;
    const deptId = box.getAttribute('data-id');
    const deptName = box.textContent.trim();

    // パンくず更新
    breadcrumb.innerHTML = `
      <span class="breadcrumb-item" data-action="home">${companyName}</span> >
      <span class="breadcrumb-item" data-action="department" data-id="${deptId}">${deptName}</span>
      <div class="new-team-form" style="margin-left: 20px; display: inline-block;">
        <input type="text" id="newTeamName" placeholder="新しいチーム名" style="padding: 4px; border: 1px solid #ddd; border-radius: 3px;">
        <button onclick="createTeam(${deptId})" style="padding: 4px 8px; margin-left: 4px; background: #28a745; color: white; border: none; border-radius: 3px; cursor: pointer;">チーム作成</button>
      </div>
    `;

    // タイトル更新
    homeTitle.textContent = `${companyName} ${deptName}`;

    // fetchでチーム一覧を取得
    fetch(`/api/departments/${deptId}/teams`)
      .then(resp => resp.json())
      .then(teams => {
        teamList.innerHTML = '';
        if (teams.length === 0) {
          teamList.innerHTML = '<div class="box">チームがありません</div>';
        } else {
          teams.forEach(t => {
            const div = document.createElement('div');
            div.className = 'box';
            div.textContent = t.name;
            div.setAttribute('data-id', t.id);
            div.addEventListener('click', () => {
              breadcrumb.innerHTML = `
                <span class="breadcrumb-item" data-action="home">${companyName}</span> >
                <span class="breadcrumb-item" data-action="department" data-id="${deptId}">${deptName}</span> >
                <span class="breadcrumb-item" data-action="team" data-id="${t.id}">${t.name}</span>
              `;
              window.location.href = `/teams/${t.id}`;
            });
            teamList.appendChild(div);
          });
        }
        // 表示切替：部署一覧を隠してチーム一覧を表示する（好みで両方可）
        deptList.style.display = 'none';
        teamList.style.display = 'flex';
      })
      .catch(err => {
        console.error(err);
        alert('チーム取得に失敗しました');
      });
  });

  // パンくずクリックで戻る処理
  breadcrumb.addEventListener('click', e => {
    const item = e.target.closest('.breadcrumb-item');
    if (!item) return;
    const action = item.getAttribute('data-action');
    if (action === 'home') {
      deptList.style.display = 'flex';
      teamList.style.display = 'none';
      homeTitle.textContent = companyName;
      breadcrumb.innerHTML = `<span class="breadcrumb-item" data-action="home">${companyName}</span>`;
    }
    if (action === 'department') {
      const deptId = item.getAttribute('data-id');
      // 再取得でも可
      document.querySelector(`.dept-box[data-id="${deptId}"]`).click();
    }
  });
});
