describe('Teste de Interação com o App', () => {
  it('Deve exibir a tela inicial e clicar nos botões', async () => {
      const viewAllChampionsButton = await $('android=new UiSelector().text("View all champions")');
      await viewAllChampionsButton.waitForDisplayed({ timeout: 10000 });
      await viewAllChampionsButton.click();

      await browser.pause(3000);
      await driver.back();

      const viewByRoleButton = await $('android=new UiSelector().text("View champions by role")');
      await viewByRoleButton.waitForDisplayed({ timeout: 10000 });
      await viewByRoleButton.click();
      await browser.pause(3000);

      const viewByRoleTankButton = await $('android=new UiSelector().text("Tank")');
      await viewByRoleTankButton.waitForDisplayed({ timeout: 10000 });
      await viewByRoleTankButton.click();
      await browser.pause(3000);

  });
});