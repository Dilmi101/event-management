const ANALYTICS_SERVICE_URL = import.meta.env.VITE_ANALYTICS_SERVICE_URL ?? 'http://localhost:8085';

// crypto.randomUUID() only exists in secure contexts (HTTPS or localhost).
// This app is served over plain HTTP via the ALB, so fall back to a
// non-cryptographic UUID v4 there instead of letting sendAnalytics throw.
function generateSessionId(): string {
    if (typeof crypto !== 'undefined' && typeof crypto.randomUUID === 'function') {
        return crypto.randomUUID();
    }
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

export async function sendAnalytics(
    eventType: string,
    page: string
) {
    try {
        const payload = {
            eventType,
            page,
            sessionId: generateSessionId(),
            browser: navigator.userAgent
        };

        await fetch(`${ANALYTICS_SERVICE_URL}/api/analytics`,{
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(payload)
        });
    } catch(error){
        console.error(error);
    }
}
