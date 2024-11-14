describe('Teste de Interação com o App', () => {
  it('Deve exibir a tela inicial e clicar nos botões', async () => {
      const viewAllChampionsButton = await $('android=new UiSelector().text("View all champions")');
      await viewAllChampionsButton.waitForDisplayed({ timeout: 10000 });
      await viewAllChampionsButton.click();
      await browser.pause(3000);

      //Aatrox Card
      const championCardDetail = await $(`//android.widget.TextView[@text='Aatrox']`);
      await championCardDetail.waitForDisplayed({ timeout: 5000 });
      await championCardDetail.click();
      await browser.pause(3000);
      
      await driver.back();

      await driver.back();

      //All Champions by Role
      const viewByRoleButton = await $('android=new UiSelector().text("View champions by role")');
      await viewByRoleButton.waitForDisplayed({ timeout: 10000 });
      await viewByRoleButton.click();
      await browser.pause(3000);

      //Tag Tank of dialog
      const viewByRoleTankButton = await $('android=new UiSelector().text("Tank")');
      await viewByRoleTankButton.waitForDisplayed({ timeout: 10000 });
      await viewByRoleTankButton.click();
      await browser.pause(3000);
      
      //Alistar Card Champion Tag
      const championByTagCardDetail = await $(`//android.widget.TextView[@text='Alistar']`);
      await championByTagCardDetail.waitForDisplayed({ timeout: 5000 });
      await championByTagCardDetail.click();
      await browser.pause(3000);

      await driver.back();

      await driver.back();
    
      //Team Drawer
      const teamDrawerButton = await $(`android=new UiSelector().text("Team Drawer")`);
      await teamDrawerButton.waitForDisplayed({ timeout: 5000 });
      await teamDrawerButton.click();
      await browser.pause(4000);
      
      //Share Teams Button
      const shareTeamsButton = await $(`android=new UiSelector().text("Share teams")`);
      await shareTeamsButton.waitForDisplayed({ timeout: 5000 });
      await shareTeamsButton.click();
      await browser.pause(4000);

      await driver.back();

      //Refresh Teams
      const refreshTeamsButton = await $(`//android.widget.ImageView[@resource-id='com.example.lol_champions_browser:id/buttonrefreshover']`);
      await refreshTeamsButton.waitForDisplayed({ timeout: 5000 });
      await refreshTeamsButton.click();
      await browser.pause(4000);
  });
});