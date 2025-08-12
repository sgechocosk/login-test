document.addEventListener('DOMContentLoaded', () => {
  const departmentList = document.getElementById('departmentList');
  const teamList = document.getElementById('teamList');
  const breadcrumb = document.getElementById('breadcrumb');

  // 部署クリックイベント
  departmentList.addEventListener('click', e => {
    const box = e.target.closest('.dept-box');
    if (!box) return;
    const deptId = box.getAttribute('data-id');
    const deptName = box.textContent.trim();
    showTeams(deptId, deptName);
  });

  // パンくずクリックイベント
  breadcrumb.addEventListener('click', e => {
    const item = e.target.closest('.breadcrumb-item');
    if (!item) return;
    const action = item.getAttribute('data-action');

    if (action === 'home') {
      // 部署一覧に戻す
      teamList.style.display = 'none';
      departmentList.style.display = 'flex';
      breadcrumb.innerHTML = `<span class="breadcrumb-item" data-action="home">${document.querySelector('#breadcrumb .breadcrumb-item').textContent}</span>`;
    }
  });

  // チーム一覧取得＆表示
  function showTeams(deptId, deptName) {
    fetch(`/api/departments/${deptId}/teams`)
      .then(res => res.json())
      .then(teams => {
        teamList.innerHTML = '';
        teams.forEach(team => {
          const div = document.createElement('div');
          div.classList.add('box', 'team-box');
          div.textContent = team.name;
          div.onclick = () => window.location.href = `/teams/${team.id}`;
          teamList.appendChild(div);
        });
        teamList.style.display = 'flex';
        departmentList.style.display = 'none';

        // パンくず更新
        const companyName = breadcrumb.querySelector('[data-action="home"]').textContent;
        breadcrumb.innerHTML = `
          <span class="breadcrumb-item" data-action="home">${companyName}</span> >
          <span class="breadcrumb-item" data-action="department" data-id="${deptId}">${deptName}</span>
        `;
        homeTitle.textContent = `${companyName} ${deptName}`;
      });
  }

  // URLパラメータからdept取得（team.html → home.html用）
  const params = new URLSearchParams(window.location.search);
  const deptIdParam = params.get('dept');
  if (deptIdParam) {
    // 部署名を取得してから表示
    const deptElem = document.querySelector(`.dept-box[data-id="${deptIdParam}"]`);
    if (deptElem) {
      showTeams(deptIdParam, deptElem.textContent.trim());
    } else {
      // ページロード時に部署名がない場合はAPIで取得
      fetch(`/api/departments/${deptIdParam}`)
        .then(res => res.json())
        .then(dept => {
          showTeams(deptIdParam, dept.name);
        });
    }
  }
});
