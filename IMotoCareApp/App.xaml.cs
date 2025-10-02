using IMotoCareApp.Helpers;

namespace IMotoCareApp;

public partial class App : Application
{
    public App()
    {
        InitializeComponent();
        var homePage = Helpers.ServiceHelper.GetService<Views.HomePage>() ?? new Views.HomePage();
        MainPage = new NavigationPage(homePage);
    }
}
