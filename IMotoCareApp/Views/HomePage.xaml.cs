using IMotoCareApp.Helpers;
using Microsoft.Extensions.DependencyInjection;

namespace IMotoCareApp.Views;

public partial class HomePage : ContentPage
{
    private readonly IServiceProvider _serviceProvider;

    public HomePage()
        : this(null)
    {
    }

    public HomePage(IServiceProvider? serviceProvider)
    {
        _serviceProvider = serviceProvider ?? ServiceHelper.Services ?? new ServiceCollection().BuildServiceProvider();
        InitializeComponent();
    }

    private async void OnMechanicClicked(object sender, EventArgs e)
    {
        await NavigateToPersonListAsync("mechanic", "Mechanics near you");
    }

    private async void OnBreakdownClicked(object sender, EventArgs e)
    {
        await NavigateToPersonListAsync("breakdown", "Breakdown support specialists");
    }

    private async Task NavigateToPersonListAsync(string type, string title)
    {
        if (_serviceProvider.GetService(typeof(PersonListPage)) is PersonListPage page)
        {
            page.BindingContext = ActivatorUtilities.CreateInstance<ViewModels.PersonListViewModel>(_serviceProvider);
            page.Initialize(type, title);
            await Navigation.PushAsync(page);
        }
    }
}
