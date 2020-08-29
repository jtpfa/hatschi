export class DateFormatter {
    static toDateString(timestamp) {
        const date = new Date(timestamp)

        return `${date.toLocaleDateString()}, ${date.toLocaleTimeString()}`
    }
}
