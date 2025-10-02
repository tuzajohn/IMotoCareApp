using IMotoCareApp.Models;
using Microsoft.Maui.ApplicationModel.Communication;

namespace IMotoCareApp.Views;

public partial class PersonDetailPage : ContentPage
{
    private Personell? _person;

    public PersonDetailPage()
    {
        InitializeComponent();
    }

    public void Bind(Personell person)
    {
        _person = person;
        BindingContext = person;
    }

    private void OnCallClicked(object sender, EventArgs e)
    {
        if (string.IsNullOrWhiteSpace(_person?.Contact))
        {
            return;
        }

        try
        {
            PhoneDialer.Open(_person.Contact);
        }
        catch (Exception)
        {
            // Swallow exception in environments without telephony support.
        }
    }
}
