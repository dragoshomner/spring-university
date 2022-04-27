const goToPage = (e) => {
    const url = new URL(window.location.href);
    url.searchParams.set('pageNumber', e.getAttribute('data-page'));
    window.location.href = url;
};

const sortByRedirect = (e) => {
    const url = new URL(window.location.href);
    if (e.value !== "sortBy") {
        url.searchParams.set('sortBy', e.value);
    } else {
        url.searchParams.delete('sortBy');
    }
    window.location.href = url;
};