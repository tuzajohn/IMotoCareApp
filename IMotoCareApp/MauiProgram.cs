using CommunityToolkit.Maui;
using IMotoCareApp.Helpers;

namespace IMotoCareApp;

public static class MauiProgram
{
    public static MauiApp CreateMauiApp()
    {
        var builder = MauiApp.CreateBuilder();
        builder
            .UseMauiApp<App>()
            .UseMauiCommunityToolkit();

        builder.Services.AddSingleton<Services.PersonelService>();
        builder.Services.AddTransient<ViewModels.PersonListViewModel>();
        builder.Services.AddTransient<Views.HomePage>();
        builder.Services.AddTransient<Views.PersonListPage>();
        builder.Services.AddTransient<Views.PersonDetailPage>();

        var app = builder.Build();
        Helpers.ServiceHelper.Services = app.Services;
        return app;
    }
}
