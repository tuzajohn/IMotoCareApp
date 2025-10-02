using System.Linq;
using System.Net.Http;
using System.Text.Json;
using IMotoCareApp.Models;

namespace IMotoCareApp.Services;

public sealed class PersonelService
{
    private const string BaseUrl = "http://centatech-001-site5.atempurl.com/";
    private readonly HttpClient _httpClient;

    public PersonelService(HttpClient? httpClient = null)
    {
        _httpClient = httpClient ?? new HttpClient
        {
            Timeout = TimeSpan.FromSeconds(15)
        };
    }

    public async Task<IReadOnlyList<Personell>> GetPeopleByTypeAsync(string type, CancellationToken cancellationToken = default)
    {
        if (string.IsNullOrWhiteSpace(type))
        {
            return Array.Empty<Personell>();
        }

        var requestUri = new Uri(new Uri(BaseUrl), $"people/type/{type}");

        try
        {
            using var response = await _httpClient.GetAsync(requestUri, cancellationToken).ConfigureAwait(false);
            response.EnsureSuccessStatusCode();
            await using var responseStream = await response.Content.ReadAsStreamAsync(cancellationToken).ConfigureAwait(false);
            using var document = await JsonDocument.ParseAsync(responseStream, cancellationToken: cancellationToken).ConfigureAwait(false);

            if (!document.RootElement.TryGetProperty("data", out var dataElement) || dataElement.ValueKind != JsonValueKind.Array)
            {
                return Array.Empty<Personell>();
            }

            var people = new List<Personell>();
            foreach (var element in dataElement.EnumerateArray())
            {
                people.Add(Personell.FromJsonElement(element));
            }

            return people;
        }
        catch (Exception)
        {
            return GetOfflineFallback(type);
        }
    }

    private static IReadOnlyList<Personell> GetOfflineFallback(string type)
    {
        var samples = new List<Personell>
        {
            new()
            {
                PersonId = Guid.NewGuid().ToString(),
                GarageId = "demo-1",
                FirstName = "Ama",
                LastName = "Boateng",
                Contact = "+233 20 000 0000",
                Type = "mechanic",
                ImageUri = "https://placehold.co/200x200?text=Mechanic",
                GarageName = "Ama's Workshop",
                Address = "Downtown Accra",
                Latitude = 5.6037,
                Longitude = -0.1870,
                DistanceSummary = "2.4 km away"
            },
            new()
            {
                PersonId = Guid.NewGuid().ToString(),
                GarageId = "demo-2",
                FirstName = "Kojo",
                LastName = "Mensah",
                Contact = "+233 24 123 4567",
                Type = "breakdown",
                ImageUri = "https://placehold.co/200x200?text=Rescue",
                GarageName = "Rapid Response Team",
                Address = "Spintex Road",
                Latitude = 5.6404,
                Longitude = -0.1730,
                DistanceSummary = "5.8 km away"
            }
        };

        return samples.Where(person => string.Equals(person.Type, type, StringComparison.OrdinalIgnoreCase)).ToList();
    }
}
